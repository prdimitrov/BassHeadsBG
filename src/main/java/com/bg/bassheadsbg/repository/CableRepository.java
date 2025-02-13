package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.other.Cable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CableRepository extends JpaRepository<Cable, Long> {
    Optional<Cable> findByMaterial(String material);
    Optional<Cable> findByBrandAndModel(String brand, String model);
}
