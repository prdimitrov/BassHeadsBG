package com.bg.bassheadsbg.model.dto.details;

import com.bg.bassheadsbg.model.interfaces.DeviceDetailsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class MultiChannelAmpDetailsDTO implements DeviceDetailsDTO {
    private Long id;
    private int price;
    List<String> allCurrencies;
    private String brand;
    private String model;
    private String amplifierClass;
    private float impedance;
    private int power;
    private String highPassFilter;
    private String lowPassFilter;
    private String subsonicFilter;
    private boolean remoteControl;
    private boolean bassBoost;
    private String lowInputLevel;
    private String highInputLevel;
    private float distortion;
    private double currentDraw;
    private short fuseRating;
    private short height;
    private short width;
    private short length;
    private byte numberOfChannels;
    private List<String> imageFiles;
}
