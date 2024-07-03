package com.bg.bassheadsbg.service;

import com.bg.bassheadsbg.model.dto.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.User;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
