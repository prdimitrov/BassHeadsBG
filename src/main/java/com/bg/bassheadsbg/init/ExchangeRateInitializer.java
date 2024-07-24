package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.entity.ExRateEntity;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExchangeRateInitializer implements CommandLineRunner {

    private final ExRateService exRateService;
    private final ApplicationEventPublisher eventPublisher;

    public ExchangeRateInitializer(ExRateService exRateService, ApplicationEventPublisher eventPublisher) {
        this.exRateService = exRateService;
        this.eventPublisher = eventPublisher;
    }

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