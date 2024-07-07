package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonoAmplifierRepository
        extends JpaRepository<MonoAmplifier, Long>, CommonFindDevice<MonoAmplifier> {
}
