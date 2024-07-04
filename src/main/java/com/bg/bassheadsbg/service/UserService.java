package com.bg.bassheadsbg.service;

import com.bg.bassheadsbg.model.dto.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.User;

import java.util.Optional;


public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<User> findByUsername(String userName);
}
