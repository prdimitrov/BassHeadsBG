package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.other.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(final String token);
}