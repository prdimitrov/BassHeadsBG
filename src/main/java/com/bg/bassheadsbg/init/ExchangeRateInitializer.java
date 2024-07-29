package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.entity.ExRateEntity;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used for initializing the exchange rates from www.exchangerates.org into the database.
 * It implements CommandLineRunner, which means it will run automatically after the application context is loaded.
 */
@Component
public class ExchangeRateInitializer implements CommandLineRunner {

    private final ExRateService exRateService;
    private final ApplicationEventPublisher eventPublisher;

    public ExchangeRateInitializer(ExRateService exRateService, ApplicationEventPublisher eventPublisher) {
        this.exRateService = exRateService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * This method is executed when the application starts. It checks if the exchange rates are already initialized.
     * If not, it fetches and updates the rates in the database. It then retrieves all exchange rates and publishes
     * an initialization event with the details of the exchange rates.
     *
     * @param args command line arguments (not used)
     * @throws Exception if an error occurs during fetching or updating exchange rates
     */
    @Override
    public void run(String... args) throws Exception {
        if (!exRateService.hasInitializedExRates()) {
            exRateService.updateRates(
                    exRateService.fetchExRates());
        }

        List<ExRateEntity> exchangeRates = exRateService.getAllExRates();

        String exchangeRateDetails = exchangeRates.stream()
                .map(rate -> rate.getCurrency() + ": " + rate.getRate())
                .collect(Collectors.joining("\n"));

        eventPublisher.publishEvent(new InitializationEvent(this, "Exchange rates:\n"
                + exchangeRateDetails
                + "\nScroll up to see the loaded exchange rates. :)"));
    }

}