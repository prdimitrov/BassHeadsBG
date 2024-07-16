package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.UserRegistrationDTO;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.repository.RoleRepository;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(mapUser(userRegistrationDTO));
    }

    @Override
    public Optional<UserEntity> findByUsername(String userName) {
        return this.userRepository.findByUsername(userName);
    }

    private UserEntity mapUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity mappedUserEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);

        mappedUserEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        UserRole userRole = roleRepository.findByRole(UserRoleEnum.USER);

        mappedUserEntity.getRoles().add(userRole);

        return mappedUserEntity;
    }
}
