package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.images.MultiChannelAmplifierImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiChannelAmplifierImageRepository extends JpaRepository<MultiChannelAmplifierImage, Long> {

    void deleteByMultiChannelAmplifier(MultiChannelAmplifier multiChannelAmplifier);
}
