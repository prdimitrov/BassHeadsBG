package com.bg.bassheadsbg.repository;

import java.util.Optional;
import java.util.Set;

public interface CommonFindDevice<T> {
    Set<T> findAllByBrand(String brand);
    Optional<T> findByBrand(String brand);
    Optional<T> findByModel(String model);
    Optional<T> findByBrandAndModel(String brand, String model);
}
