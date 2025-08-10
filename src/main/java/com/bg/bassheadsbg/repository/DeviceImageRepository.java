package com.bg.bassheadsbg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DeviceImageRepository<I, D> extends JpaRepository<I, Long> {
    void deleteByDevice(final D device);
}