package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HighRangeRepository extends JpaRepository<HighRange, Long> {
    Optional<HighRange> findByBrandAndModel(String brand, String model);
}
