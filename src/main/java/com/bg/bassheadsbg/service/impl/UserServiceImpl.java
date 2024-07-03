package com.bg.bassheadsbg.service.impl;

import com.bg.bassheadsbg.model.dto.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.enums.UserRoleEnum;
import com.bg.bassheadsbg.model.entity.users.User;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.repository.RoleRepository;
import com.bg.bassheadsbg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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

    private User mapUser(UserRegistrationDTO userRegistrationDTO) {
        User mappedUser = modelMapper.map(userRegistrationDTO, User.class);

        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        UserRole userRole = roleRepository.findByRole(UserRoleEnum.USER);

        mappedUser.getRoles().add(userRole);

        return mappedUser;
    }
}
