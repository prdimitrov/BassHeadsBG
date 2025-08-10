package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface DeviceRepository<D extends DeviceEntity<?>> extends JpaRepository<D, Long> {
    String FIND_BY_BRAND_AND_MODEL_QUERY = "SELECT e FROM #{#entityName} e " +
            "LEFT JOIN e.userLikes ul " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(e.id) DESC, " +
            "LOWER(e.brand) ASC, " +
            "LOWER(e.model) ASC";
    String FIND_ALL_WITH_USER_LIKES_ORDER_BY_BRAND_AND_MODEL_QUERY = "SELECT e FROM #{#entityName} e " +
            "LEFT JOIN e.userLikes ul " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(e.id) DESC, " +
            "LOWER(e.brand) ASC, " +
            "LOWER(e.model) ASC";

    String FIND_BY_USER_LIKES_QUERY = "SELECT e FROM #{#entityName} e " +
            "LEFT JOIN e.userLikes AS ul " +
            "WHERE e.id = :deviceId";

    @Query(FIND_BY_BRAND_AND_MODEL_QUERY)
    Optional<D> findByBrandAndModel(final String brand, final String model);

    @Query(FIND_ALL_WITH_USER_LIKES_ORDER_BY_BRAND_AND_MODEL_QUERY)
    List<D> findAllDevicesWithUserLikesCountOrderByBrandAndModel();

    @Query(FIND_BY_USER_LIKES_QUERY)
    Optional<D> findDeviceByUserLikes(@Param("deviceId") final Long deviceId);
}