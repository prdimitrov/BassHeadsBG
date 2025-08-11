package com.bg.bassheadsbg.model.dto.details;

import com.bg.bassheadsbg.model.interfaces.DeviceDetailsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class SubwooferDetailsDTO implements DeviceDetailsDTO {
    private Long id;
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
    private float coilHeight;
    private byte coilLayers;
    private short magnetSize;
    private float weight;
    private float vas;
    private byte xmax;
    private float qms;
    private float qes;
    private float qts;
    private float sd;
    private float bl;
    private float mms;
    private List<String> imageFiles;
}