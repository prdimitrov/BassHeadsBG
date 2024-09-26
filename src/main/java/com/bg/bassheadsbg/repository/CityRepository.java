package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c.localizedName FROM City c")
    List<String> findAllByLocalizedNames();
}
