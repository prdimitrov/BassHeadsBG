package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubwooferRepository
        extends JpaRepository<Subwoofer, Long> {
    Optional<Subwoofer> findByBrandAndModel(String brand, String model);

    @Query("SELECT s FROM  Subwoofer s " +
            "LEFT JOIN s.userLikes sul " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(sul.id) DESC, " +
            "LOWER(s.brand) ASC, " +
            "LOWER(s.model) ASC")
    List<Subwoofer> findAllSubwoofersWithUserLikesCountOrderByBrandAndModel();
}
