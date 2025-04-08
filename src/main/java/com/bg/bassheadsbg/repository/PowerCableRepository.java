package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.cables.PowerCable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PowerCableRepository extends JpaRepository<PowerCable, Long> {
    Optional<PowerCable> findByMaterial(String material);

    Optional<PowerCable> findByBrandAndModel(String brand, String model);

    @Query("SELECT pc FROM PowerCable pc " +
            "LEFT JOIN pc.userLikes pcul " +
            "GROUP BY pc.id " +
            "ORDER BY COUNT(pcul.id) DESC, " +
            "LOWER(pc.brand) ASC, " +
            "LOWER(pc.model) ASC")
    List<PowerCable> findAllPowerCablesUserLikesCountOrderByBrandAndModel();

    @Query("SELECT pc FROM PowerCable pc " +
            "LEFT JOIN pc.userLikes ul " +
            "WHERE pc.id = :powerCableId")
    Optional<PowerCable> findPowerCableByUserLikes(@Param("powerCableId") Long id);
}
