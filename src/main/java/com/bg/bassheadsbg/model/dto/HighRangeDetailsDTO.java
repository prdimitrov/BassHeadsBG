package com.bg.bassheadsbg.model.dto;

public record HighRangeDetailsDTO(
        Long id,
        String brand,
        String model,
        Float sensitivity,
        float size,
        float frequencyResponse,
        byte numberOfCoils,
        byte impedance,
        short powerHandling,
        String material,
        int frequencyRangeFrom,
        int frequencyRangeTo,
        String crossover) {

}
