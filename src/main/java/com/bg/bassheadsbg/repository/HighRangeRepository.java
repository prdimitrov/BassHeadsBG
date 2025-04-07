package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighRangeRepository
        extends JpaRepository<HighRange, Long> {
    Optional<HighRange> findByBrandAndModel(String brand, String model);

    @Query("SELECT hr FROM HighRange hr " +
            "LEFT JOIN hr.userLikes hrul " +
            "GROUP BY hr.id " +
            "ORDER BY COUNT(hrul.id) DESC, " +
            "LOWER(hr.brand) ASC, " +
            "LOWER(hr.model) ASC")
    List<HighRange> findAllHighRangesWithUserLikesCountOrderByBrandAndModel();
}
