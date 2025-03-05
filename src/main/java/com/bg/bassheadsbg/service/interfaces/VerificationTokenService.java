package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.entity.other.VerificationToken;
import com.bg.bassheadsbg.model.entity.users.UserEntity;

public interface VerificationTokenService {
    String createVerificationToken(UserEntity user);

    VerificationToken findByToken(String token);

    boolean isTokenExpired(VerificationToken token);
}
