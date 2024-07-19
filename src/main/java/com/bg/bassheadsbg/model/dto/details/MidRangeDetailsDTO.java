package com.bg.bassheadsbg.model.dto.details;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class MidRangeDetailsDTO {
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
    private List<String> images;
    private String imagesString;

    public void setImages(List<String> images) {
        this.images = images;
        if (images != null && !images.isEmpty()) {
            this.imagesString = String.join(", ", images);
        } else {
            this.imagesString = "";
        }
    }
}