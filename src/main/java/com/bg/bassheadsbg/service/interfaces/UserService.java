package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.UserEntity;

import java.util.Optional;


public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<UserEntity> findByUsername(String username);

    void addRoleToUserId(String role, Long userId);

    void removeRoleToUserId(String role, Long userId);

    UserEntity banUser(Long userId);

    UserEntity unbanUser(Long userId);

}
