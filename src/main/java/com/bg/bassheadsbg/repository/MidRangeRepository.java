package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MidRangeRepository
        extends JpaRepository<MidRange, Long> {
    Optional<MidRange> findByBrandAndModel(String brand, String model);

    @Query("SELECT mr FROM MidRange mr " +
            "LEFT JOIN mr.userLikes mul " +
            "GROUP BY mr.id " +
            "ORDER BY COUNT(mul.id) DESC, " +
            "LOWER(mr.brand) ASC, " +
            "LOWER(mr.model) ASC")
    List<MidRange> findAllMidRangesWithUserLikesCountOrderByBrandAndModel();

    @Query("SELECT mr FROM MidRange mr " +
            "LEFT JOIN mr.userLikes AS ul " +
            "WHERE mr.id = :midRangeId")
    Optional<MidRange> findHighRangeByUserLikes(@Param("midRangeId") Long id);
}
