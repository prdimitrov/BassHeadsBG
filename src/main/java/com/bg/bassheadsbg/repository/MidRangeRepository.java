package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MidRangeRepository extends JpaRepository<MidRange, Long> {
    Optional<MidRange> findByBrandAndModel(String brand, String model);
}
