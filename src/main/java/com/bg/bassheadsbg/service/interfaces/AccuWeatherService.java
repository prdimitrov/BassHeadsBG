package com.bg.bassheadsbg.service.interfaces;

import java.util.List;

public interface AccuWeatherService {

    void initializeAllCitiesInBulgaria();
    boolean hasInitializedCities();
    List<String> getAllCitiesFromDb();
}
