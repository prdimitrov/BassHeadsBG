package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.images.SubwooferImage;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubwooferImageRepository extends JpaRepository<SubwooferImage, Long> {

    void deleteBySubwoofer(Subwoofer subwoofer);
}
