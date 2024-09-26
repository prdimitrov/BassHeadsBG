package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.service.implementation.AccuWeatherServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CityInitializer implements CommandLineRunner {

    private final AccuWeatherServiceImpl accuWeatherService;

    public CityInitializer(AccuWeatherServiceImpl accuWeatherService) {
        this.accuWeatherService = accuWeatherService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!accuWeatherService.hasInitializedTowns()) {
            try {
                accuWeatherService.getAllTownsInBulgaria();
                log.info("City initialization completed.");
            } catch (Exception e) {
                log.error("Error during city initialization: ", e);
            }
        }
    }
}