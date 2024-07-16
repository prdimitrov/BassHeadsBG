package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.UserEntity;

import java.util.Optional;


public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<UserEntity> findByUsername(String userName);
}
