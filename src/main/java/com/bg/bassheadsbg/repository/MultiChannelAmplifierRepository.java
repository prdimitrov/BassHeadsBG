package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MultiChannelAmplifierRepository
        extends JpaRepository<MultiChannelAmplifier, Long> {
    Optional<MultiChannelAmplifier> findByBrandAndModel(String brand, String model);

    @Query("SELECT mc FROM MultiChannelAmplifier mc " +
            "LEFT JOIN mc.userLikes mcul " +
            "GROUP BY mc.id " +
            "ORDER BY COUNT(mcul.id) DESC, " +
            "LOWER(mc.brand) ASC, " +
            "LOWER(mc.model) ASC")
    List<MultiChannelAmplifier> findAllMultiChannelAmpsUserLikesCountOrderByBrandAndModel();

    @Query("SELECT mc FROM MultiChannelAmplifier mc " +
            "LEFT JOIN mc.userLikes ul " +
            "WHERE mc.id = :multiChannelAmplifierId")
    Optional<MultiChannelAmplifier> findMultiChannelAmplifierByUserLikes(@Param("multiChannelAmplifierId") Long id);
}
