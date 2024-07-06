package com.bg.bassheadsbg.model.dto;

public record MidRangeDetailsDTO(
        Long id,
        String brand,
        String model,
        Float sensitivity,
        float size,
        float frequencyResponse,
        byte numberOfCoils,
        byte impedance,
        short powerHandling,
        int frequencyRangeFrom,
        int frequencyRangeTo) {

}
