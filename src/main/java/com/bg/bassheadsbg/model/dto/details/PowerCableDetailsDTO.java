package com.bg.bassheadsbg.model.dto.details;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PowerCableDetailsDTO {
    private long id;
    private int price;
    List<String> allCurrencies;
    private String brand;
    private String model;
    private float length;
    private String material;
    private float thickness;
    private String description;
    private List<String> imageFiles;
}
