package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwooferRepository
        extends JpaRepository<Subwoofer, Long>, CommonFindDevice<Subwoofer> {
}
