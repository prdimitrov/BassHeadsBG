package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.entity.City;

import java.util.List;

public interface CityService {

    City getCityById(Long id);
    List<City> getAllCities();
}
