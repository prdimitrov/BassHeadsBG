package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighRangeRepository
        extends JpaRepository<HighRange, Long>, CommonFindDevice<HighRange> {

}
