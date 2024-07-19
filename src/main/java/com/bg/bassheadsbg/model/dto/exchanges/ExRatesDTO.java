package com.bg.bassheadsbg.model.dto.exchanges;

import java.math.BigDecimal;
import java.util.Map;


public record ExRatesDTO(String base, Map<String, BigDecimal> rates) {

}