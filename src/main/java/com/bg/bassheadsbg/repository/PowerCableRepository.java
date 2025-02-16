package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.cables.PowerCable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PowerCableRepository extends JpaRepository<PowerCable, Long> {
    Optional<PowerCable> findByMaterial(String material);
    Optional<PowerCable> findByBrandAndModel(String brand, String model);
}
