package com.bg.bassheadsbg.model.dto.exchanges;

import java.math.BigDecimal;

public record ConversionResultDTO(String from, String to, BigDecimal amount, BigDecimal result) {

}
