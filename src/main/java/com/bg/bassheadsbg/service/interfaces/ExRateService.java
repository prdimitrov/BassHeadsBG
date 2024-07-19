package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.exchanges.ExRatesDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ExRateService {

    List<String> allSupportedCurrencies();
    boolean hasInitializedExRates();

    ExRatesDTO fetchExRates();

    void updateRates(ExRatesDTO exRatesDTO);

    BigDecimal convert(String from, String to, BigDecimal amount);
}
