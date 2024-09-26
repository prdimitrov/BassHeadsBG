package com.bg.bassheadsbg.service.interfaces;

import java.util.List;

public interface AccuWeatherService {

    List<String> getAllTownsInBulgaria();
    boolean hasInitializedTowns();
}
