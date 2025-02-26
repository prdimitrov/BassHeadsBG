package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MultiChannelAmplifierRepository
        extends JpaRepository<MultiChannelAmplifier, Long> {
    Optional<MultiChannelAmplifier> findByBrandAndModel(String brand, String model);
}
