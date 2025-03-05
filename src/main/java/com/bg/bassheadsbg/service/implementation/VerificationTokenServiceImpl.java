package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.other.VerificationToken;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.VerificationTokenRepository;
import com.bg.bassheadsbg.service.interfaces.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String createVerificationToken(UserEntity user) {
        String token = java.util.UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationToken.setExpiryDate(calculateExpiryDate());
        tokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public VerificationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public boolean isTokenExpired(VerificationToken token) {
        Calendar cal = Calendar.getInstance();
        return token.getExpiryDate().before(cal.getTime());
    }

    private static Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, VerificationToken.getExpiration());
        return new Date(calendar.getTime().getTime());
    }
}
