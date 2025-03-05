package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.event.OnRegistrationCompleteEvent;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.UserEntityEditDTO;
import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import com.bg.bassheadsbg.model.entity.other.VerificationToken;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.repository.VerificationTokenRepository;
import com.bg.bassheadsbg.service.interfaces.CityService;
import com.bg.bassheadsbg.service.interfaces.RoleService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import com.bg.bassheadsbg.service.interfaces.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CityService cityService;
    private final VerificationTokenRepository tokenRepository;
    private final VerificationTokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleService roleService,
                           CityService cityService,
                           VerificationTokenRepository tokenRepository, VerificationTokenService tokenService, ApplicationEventPublisher eventPublisher) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.cityService = cityService;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO, HttpServletRequest request) {
        UserEntity registeredUser = userRepository.save(mapUser(userRegistrationDTO));

        String token = tokenService.createVerificationToken(registeredUser);

        String appUrl = request.getContextPath();

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                registeredUser,
                request.getLocale(),
                appUrl,
                token));
    }

    @Override
    public String confirmRegistration(String token) {
        VerificationToken verificationToken = tokenService.findByToken(token);

        if (verificationToken == null) {
            return "error/went-wrong";  // Invalid token
        }

        if (tokenService.isTokenExpired(verificationToken)) {
            return "error/token-expired";
        }

        UserEntity user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return "index";
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUserId(String role, Long userId) {
        UserRole userRole = roleService.findByName(UserRoleEnum.valueOf(role));
        if (userRole != null) {
            userRepository.findById(userId).ifPresent(user -> {
                if (!user.getRoles().contains(userRole)) {
                    user.getRoles().add(userRole);
                    userRepository.save(user);
                }
            });
        }
    }

    @Override
    public void removeRoleToUserId(String role, Long userId) {
        UserRoleEnum roleEnum = UserRoleEnum.valueOf(role);
        userRepository.findById(userId).ifPresent(user -> {
            user.getRoles().removeIf(userRole -> userRole.getRole().equals(roleEnum));
            userRepository.save(user);
        });
    }

    @Override
    public UserEntity enableUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userEntity.setEnabled(true);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity disableUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userEntity.setEnabled(false);
        return userRepository.save(userEntity);
    }

    @Override
    public boolean isAccountDisabled(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return !user.isEnabled();
    }

    @Override
    public void updateUser(UserEntityEditDTO userEntityEditDTO) {
        UserEntity userEntity = userRepository.findById(userEntityEditDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(userEntityEditDTO.getId()));

        modelMapper.map(userEntityEditDTO, userEntity);

        if (userEntityEditDTO.getProfilePictureFile() != null && !userEntityEditDTO.getProfilePictureFile().isEmpty()) {
            try {
                byte[] profilePictureBytes = userEntityEditDTO.getProfilePictureFile().getBytes();
                userEntity.setProfilePicture(profilePictureBytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }

        userRepository.save(userEntity);
    }

    @Override
    public UserEntityEditDTO getUserDetails(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof BassHeadsUserDetails userDetails)) {
            throw new UserNotAuthenticatedException(ExceptionMessages.USER_NOT_AUTH);
        }

        Long authenticatedUserId = userDetails.getId();
        if (!authenticatedUserId.equals(id)) {
            throw new AccessDeniedException("You are not authorized to edit this profile!");
        }

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserEntityEditDTO editDTO = toUserEntityEditDTO(userEntity);

        editDTO.setProfilePictureBase64(userEntity.getProfilePictureBase64());

        return editDTO;
    }

    private UserEntityEditDTO toUserEntityEditDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserEntityEditDTO.class);
    }

    private UserEntity mapUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity mappedUserEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);
        mappedUserEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        UserRole userRole = roleService.findByName(UserRoleEnum.USER);
        mappedUserEntity.getRoles().add(userRole);
        return mappedUserEntity;
    }
}