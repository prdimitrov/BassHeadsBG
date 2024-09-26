package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.service.interfaces.AccuWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CityInitializer implements CommandLineRunner {

    private final AccuWeatherService accuWeatherService;
    private final ApplicationEventPublisher eventPublisher;

    public CityInitializer(AccuWeatherService accuWeatherService, ApplicationEventPublisher eventPublisher) {
        this.accuWeatherService = accuWeatherService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void run(String... args) {
        if (!accuWeatherService.hasInitializedCities()) {
            try {
                accuWeatherService.initializeAllCitiesInBulgaria();
                log.info("City initialization completed.");
            } catch (Exception e) {
                log.error("Error during city initialization: ", e);
            } finally {
                eventPublisher.publishEvent(new InitializationEvent(this, "Cities initialized:\n" + accuWeatherService.getAllCitiesFromDb()));
            }
        }
    }
}