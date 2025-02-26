package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.images.MonoAmplifierImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonoAmplifierImageRepository extends JpaRepository<MonoAmplifierImage, Long> {

    void deleteByMonoAmplifier(MonoAmplifier monoAmplifier);
}
