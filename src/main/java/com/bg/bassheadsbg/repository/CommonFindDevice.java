package com.bg.bassheadsbg.repository;

import java.util.List;
import java.util.Optional;

public interface CommonFindDevice<T> {
    Optional<T> findByBrandAndModel(String brand, String model);
    List<T> findByImagesContaining(String imageUrl);
}
