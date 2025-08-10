package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.interfaces.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface DeviceRepository<D extends DeviceEntity<?>> extends JpaRepository<D, Long> {
    String FIND_BY_BRAND_AND_MODEL = "SELECT e FROM #{#entityName} e " +
        "WHERE LOWER(e.brand) = LOWER(:brand) " +
        "AND LOWER(e.model) = LOWER(:model)";

    String FIND_OTHER_BY_BRAND_AND_MODEL = "SELECT e FROM #{#entityName} e " +
            "WHERE LOWER(e.brand) = LOWER(:brand) " +
            "AND LOWER(e.model) = LOWER(:model) " +
            "AND e.id <> :excludeId";

    String FIND_ALL_WITH_USER_LIKES_ORDER_BY_BRAND_AND_MODEL = "SELECT e FROM #{#entityName} e " +
        "ORDER BY SIZE(e.userLikes) DESC, " +
        "LOWER(e.brand) ASC, " +
        "LOWER(e.model) ASC";

    String FIND_BY_USER_LIKES = "SELECT e FROM #{#entityName} e " +
            "LEFT JOIN e.userLikes AS ul " +
            "WHERE e.id = :deviceId";

    @Query(FIND_BY_BRAND_AND_MODEL)
    Optional<D> findByBrandAndModel(@Param("brand") final String brand, @Param("model") final String model);

    @Query(FIND_OTHER_BY_BRAND_AND_MODEL)
    Optional<D> findOtherByBrandAndModel(@Param("brand") final String brand,
                                         @Param("model") final String model,
                                         @Param("excludeId") final long excludeId);

    @Query(FIND_ALL_WITH_USER_LIKES_ORDER_BY_BRAND_AND_MODEL)
    List<D> findAllDevicesWithUserLikesCountOrderByBrandAndModel();

    @Query(FIND_BY_USER_LIKES)
    Optional<D> findDeviceByUserLikes(@Param("deviceId") final Long deviceId);
}