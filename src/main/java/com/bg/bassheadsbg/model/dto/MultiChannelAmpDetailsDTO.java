package com.bg.bassheadsbg.model.dto;

public record MultiChannelAmpDetailsDTO(
        Long id,
        String brand,
        String model,
        String amplifierClass,
        byte impedance,
        int power,
        byte efficiency,
        String highPassFilter,
        String lowPassFilter,
        String subsonicFilter,
        boolean remoteControl,
        boolean bassBoost,
        String lowInputLevel,
        String highInputLevel,
        Float distortion,
        double currentDraw,
        short fuseRating,
        Short height,
        Short width,
        Short length,
        byte numberOfChannels) {

}
