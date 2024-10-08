package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.City;
import com.bg.bassheadsbg.repository.CityRepository;
import com.bg.bassheadsbg.service.interfaces.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new NullPointerException("City with ID: " + id + " was not found!"));
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
