package com.bg.bassheadsbg.model.dto.details;

import com.bg.bassheadsbg.model.interfaces.DetailsDeviceDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class MidRangeDetailsDTO implements DetailsDeviceDTO {
    private long id;
    private int price;
    List<String> allCurrencies;
    private String brand;
    private String model;
    private float sensitivity;
    private float size;
    private float frequencyResponse;
    private byte numberOfCoils;
    private float impedance;
    private short powerHandling;
    private int frequencyRangeFrom;
    private int frequencyRangeTo;
    private List<String> imageFiles;
}