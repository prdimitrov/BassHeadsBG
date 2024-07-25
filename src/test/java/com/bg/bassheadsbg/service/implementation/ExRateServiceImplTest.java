package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.config.ForexApiConfig;
import com.bg.bassheadsbg.exception.ApiNotFoundException;
import com.bg.bassheadsbg.model.dto.exchanges.ExRatesDTO;
import com.bg.bassheadsbg.model.entity.ExRateEntity;
import com.bg.bassheadsbg.repository.ExRateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExRateServiceImplTest {

    private static final class TestRates{
        // SUD -> base
        // CUR1 -> 4
        // CUR2 -> 0.5

        private static final String BASE_CURRENCY = "SUD";
        private static final ExRateEntity CUR1 = new ExRateEntity()
                .setCurrency("CUR1").setRate(new BigDecimal("4"));

        private static final ExRateEntity CUR2 = new ExRateEntity()
                .setCurrency("CUR2").setRate(new BigDecimal("0.5"));
        private static final List<ExRateEntity> EX_RATE_ENTITIES = List.of(CUR1, CUR2);
    }

    private ExRateServiceImpl toTest;

    @Mock(strictness = Mock.Strictness.LENIENT)
    private ExRateRepository mockRepository;

    @BeforeEach
    void setUp() {
        toTest = new ExRateServiceImpl(
                mockRepository,
                null,
                new ForexApiConfig().setBase(TestRates.BASE_CURRENCY)
        );
    }

    // 1 SUD -> 4 CUR1
    // 1 SUD -> 0.5 CUR2

    @ParameterizedTest(name = "Converting {2} {0} to {1}. Expected {3}")
    @CsvSource(
            textBlock = """
                    SUD, CUR1, 1, 4.00
                    SUD, CUR1, 2, 8.00
                    SUD, SUD, 1, 1
                    CUR1, CUR2, 1, 0.12
                    CUR2, CUR1, 1, 8.00
                    LBD, LBD, 1, 1
                    """
    )
    void testConvert(String from, String to, BigDecimal amount, BigDecimal expected) {

        initExRates();

        BigDecimal result = toTest.convert(from, to, amount);
        assertEquals(expected, result);
    }

    @Test
    void testApiObjectNotFoundException() {
        Assertions.assertThrows(ApiNotFoundException.class,
                () -> toTest.convert("NO_EXIST_1", "NO_EXIST_2", BigDecimal.ONE));
    }

    private void initExRates() {
        when(mockRepository.findByCurrency(TestRates.CUR1.getCurrency()))
                .thenReturn(Optional.of(TestRates.CUR1));
        when(mockRepository.findByCurrency(TestRates.CUR2.getCurrency()))
                .thenReturn(Optional.of(TestRates.CUR2));
    }

    @Test
    void testHasInitializedExRates() {
        when(mockRepository.count()).thenReturn(0L);
        Assertions.assertFalse(toTest.hasInitializedExRates());

        when(mockRepository.count()).thenReturn(-5L);
        Assertions.assertFalse(toTest.hasInitializedExRates());

        when(mockRepository.count()).thenReturn(6L);
        Assertions.assertTrue(toTest.hasInitializedExRates());

    }

    @Test
    void testUpdateRatesWithValidData() {
        ExRatesDTO exRatesDTO = new ExRatesDTO("SUD", Map.of("CUR1", new BigDecimal("4"), "CUR2", new BigDecimal("0.5")));
        when(mockRepository.findByCurrency("CUR1")).thenReturn(Optional.empty());
        when(mockRepository.findByCurrency("CUR2")).thenReturn(Optional.of(TestRates.CUR2));

        toTest.updateRates(exRatesDTO);
    }

    @Test
    void testConvertWhenConversionNotPossible() {
        Assertions.assertThrows(ApiNotFoundException.class, () -> toTest.convert("NO_EXIST_1", "NO_EXIST_2", BigDecimal.ONE));
    }

    @Test
    void testUpdateRatesWithInvalidBaseCurrency() {
        ExRatesDTO exRatesDTO = new ExRatesDTO("INVALID_BASE", Map.of("CUR1", new BigDecimal("4"), "CUR2", new BigDecimal("0.5")));

        Assertions.assertThrows(IllegalArgumentException.class, () -> toTest.updateRates(exRatesDTO));
    }

    @Test
    void testGetAllExRates() {
        ExRateEntity cur1 = new ExRateEntity().setCurrency("CUR1").setRate(new BigDecimal("4"));
        ExRateEntity cur2 = new ExRateEntity().setCurrency("CUR2").setRate(new BigDecimal("0.5"));

        List<ExRateEntity> expectedRates = List.of(cur1, cur2);

        when(mockRepository.findAll()).thenReturn(expectedRates);

        List<ExRateEntity> actualRates = toTest.getAllExRates();

        assertEquals(expectedRates, actualRates);
    }

    @Test
    void testAllSupportedCurrencies() {
        // Arrange
        when(mockRepository.findAll()).thenReturn(TestRates.EX_RATE_ENTITIES);

        // Act
        List<String> actualCurrencies = toTest.allSupportedCurrencies();

        // Assert
        List<String> expectedCurrencies = List.of("CUR1", "CUR2");
        assertEquals(expectedCurrencies, actualCurrencies);
    }
}
