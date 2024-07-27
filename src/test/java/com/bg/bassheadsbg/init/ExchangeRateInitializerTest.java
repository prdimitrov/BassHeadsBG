package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.entity.ExRateEntity;
import com.bg.bassheadsbg.model.dto.exchanges.ExRatesDTO;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ExchangeRateInitializerTest {

    private ExRateService exRateService;
    private ApplicationEventPublisher eventPublisher;
    private ExchangeRateInitializer exchangeRateInitializer;

    @BeforeEach
    public void setUp() {
        exRateService = Mockito.mock(ExRateService.class);
        eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
        exchangeRateInitializer = new ExchangeRateInitializer(exRateService, eventPublisher);
    }

    @Test
    public void testRunExRatesNotInitialized() throws Exception {
        when(exRateService.hasInitializedExRates()).thenReturn(false);

        Map<String, BigDecimal> ratesMap = Map.of(
                "USD", BigDecimal.valueOf(1.0),
                "EUR", BigDecimal.valueOf(0.85),
                "LEV", BigDecimal.valueOf(1.74),
                "RON", BigDecimal.valueOf(4.53)
        );
        ExRatesDTO mockExRatesDTO = new ExRatesDTO("USD", ratesMap);

        List<ExRateEntity> mockRates = Arrays.asList(
                createExRateEntity("USD", 1.0),
                createExRateEntity("EUR", 0.85),
                createExRateEntity("LEV", 1.74),
                createExRateEntity("RON", 4.53)
        );
        when(exRateService.fetchExRates()).thenReturn(mockExRatesDTO);
        when(exRateService.getAllExRates()).thenReturn(mockRates);

        exchangeRateInitializer.run();

        verify(exRateService).updateRates(mockExRatesDTO);

        ArgumentCaptor<InitializationEvent> eventCaptor = ArgumentCaptor.forClass(InitializationEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        InitializationEvent publishedEvent = eventCaptor.getValue();
        String expectedMessage = "Exchange rates:\nUSD: 1.0\nEUR: 0.85\nLEV: 1.74\nRON: 4.53\nScroll up to see the loaded exchange rates. :)";
        assertEquals(expectedMessage, publishedEvent.getMessage());
    }

    @Test
    public void testRunExRatesAlreadyInitialized() throws Exception {
        when(exRateService.hasInitializedExRates()).thenReturn(true);

        List<ExRateEntity> mockRates = Arrays.asList(
                createExRateEntity("USD", 1.0),
                createExRateEntity("EUR", 0.85),
                createExRateEntity("LEV", 1.74),
                createExRateEntity("RON", 4.53)
        );
        when(exRateService.getAllExRates()).thenReturn(mockRates);

        exchangeRateInitializer.run();

        verify(exRateService, never()).updateRates(any());

        ArgumentCaptor<InitializationEvent> eventCaptor = ArgumentCaptor.forClass(InitializationEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        InitializationEvent publishedEvent = eventCaptor.getValue();
        String expectedMessage = "Exchange rates:\nUSD: 1.0\nEUR: 0.85\nLEV: 1.74\nRON: 4.53\nScroll up to see the loaded exchange rates. :)";
        assertEquals(expectedMessage, publishedEvent.getMessage());
    }

    private ExRateEntity createExRateEntity(String currency, double rate) {
        ExRateEntity exRateEntity = new ExRateEntity();
        exRateEntity.setCurrency(currency);
        exRateEntity.setRate(BigDecimal.valueOf(rate));
        return exRateEntity;
    }
}
