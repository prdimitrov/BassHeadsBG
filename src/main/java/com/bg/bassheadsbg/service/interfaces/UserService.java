package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.UserEntityEditDTO;
import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;


public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO, HttpServletRequest request);

    String confirmRegistration(String token);

    List<UserEntity> findAllUsers();

    Optional<UserEntity> findByUsername(String username);

    void addRoleToUserId(String role, Long userId);

    void removeRoleToUserId(String role, Long userId);

    UserEntity enableUser(Long userId);

    UserEntity disableUser(Long userId);

    boolean isAccountDisabled(String username);

    void updateUser(UserEntityEditDTO userEntityEditDTO);

    UserEntityEditDTO getUserDetails(Long id);
}
