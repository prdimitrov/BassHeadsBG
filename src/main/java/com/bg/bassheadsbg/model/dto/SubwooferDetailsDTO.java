package com.bg.bassheadsbg.model.dto;

public record SubwooferDetailsDTO(
        Long id,
        String brand,
        String model,
        Float sensitivity,
        float size,
        float frequencyResponse,
        byte numberOfCoils,
        byte impedance,
        short powerHandling,
        float coilHeight,
        Byte coilLayers,
        short magnetSize,
        Float vas,
        byte xmax,
        Float qms,
        Float qes,
        Float qts,
        Float sd,
        Float bl,
        float mms) {

}