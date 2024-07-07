package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MultiChannelAmplifierRepository
        extends JpaRepository<MultiChannelAmplifier, Long>, CommonFindDevice<MultiChannelAmplifier> {
}
