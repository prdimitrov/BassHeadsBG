package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.ExRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExRateRepository extends JpaRepository<ExRateEntity, Long> {
    Optional<ExRateEntity> findByCurrency(final String currency);
}