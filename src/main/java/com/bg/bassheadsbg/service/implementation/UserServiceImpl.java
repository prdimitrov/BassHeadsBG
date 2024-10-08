package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.UserEntityEditDTO;
import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import com.bg.bassheadsbg.model.entity.City;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.CityService;
import com.bg.bassheadsbg.service.interfaces.RoleService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CityService cityService;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService, CityService cityService) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.cityService = cityService;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(mapUser(userRegistrationDTO));
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

    private UserEntity mapUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity mappedUserEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);
        mappedUserEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        UserRole userRole = roleService.findByName(UserRoleEnum.USER);
        mappedUserEntity.getRoles().add(userRole);
        return mappedUserEntity;
    }

    @Override
    @Transactional
    public void setCityToUserId(Long userId, Long cityId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        City city = cityService.getCityById(cityId);
        userEntity.setCity(city);
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

        return userRepository.findById(id)
                .map(this::toUserEntityEditDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private UserEntityEditDTO toUserEntityEditDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserEntityEditDTO.class);
    }

}