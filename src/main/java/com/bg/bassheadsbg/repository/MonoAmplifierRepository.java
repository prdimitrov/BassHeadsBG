package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonoAmplifierRepository
        extends JpaRepository<MonoAmplifier, Long> {
    Optional<MonoAmplifier> findByBrandAndModel(String brand, String model);

    @Query("SELECT ma FROM MonoAmplifier ma " +
            "LEFT JOIN ma.userLikes maul " +
            "GROUP BY ma.id " +
            "ORDER BY COUNT(maul.id) DESC, " +
            "LOWER(ma.brand) ASC, " +
            "LOWER(ma.model) ASC")
    List<MonoAmplifier> findAllMonoAmplifiersCountUserLikesOrderByBrandAndModel();

    @Query("SELECT ma FROM MonoAmplifier ma " +
            "LEFT JOIN ma.userLikes ul " +
            "WHERE ma.id = :monoAmplifierId")
    Optional<MonoAmplifier> findMonoAmplifierByUserLikes(@Param("monoAmplifierId") Long id);
}
