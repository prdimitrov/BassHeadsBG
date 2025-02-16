package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.cables.PowerCable;
import com.bg.bassheadsbg.model.entity.images.PowerCableImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PowerCableImageRepository extends JpaRepository<PowerCableImage, Long> {

    void deleteByPowerCable(PowerCable powerCable);
}
