package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.images.MidRangeImage;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MidRangeImageRepository extends JpaRepository<MidRangeImage, Long> {

    void deleteByMidRange(MidRange midRange);
}
