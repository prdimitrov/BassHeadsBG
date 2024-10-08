package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.UserEntityEditDTO;
import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.UserEntity;

import java.util.List;
import java.util.Optional;


public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    List<UserEntity> findAllUsers();

    Optional<UserEntity> findByUsername(String username);

    void addRoleToUserId(String role, Long userId);

    void removeRoleToUserId(String role, Long userId);

    UserEntity enableUser(Long userId);

    UserEntity disableUser(Long userId);

    boolean isAccountDisabled(String username);

    void setCityToUserId(Long userId, Long cityId);

    UserEntityEditDTO getUserDetails(Long id);
}
