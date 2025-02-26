package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.images.HighRangeImage;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HighRangeImageRepository extends JpaRepository<HighRangeImage, Long> {

    void deleteByHighRange(HighRange highRange);
}
