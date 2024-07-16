package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.validation.ValidUrlList;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class BaseSpeaker extends BaseEntity {
    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
    @NotNull
    private float sensitivity;

    @Positive
    @NotNull
    private float size;

    @PositiveOrZero
    @NotNull
    private float frequencyResponse;

    @PositiveOrZero
    @NotNull
    private byte numberOfCoils;

    @PositiveOrZero
    @NotNull
    private byte impedance;

    @PositiveOrZero
    @NotNull
    private short powerHandling;

    @ValidUrlList
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "images")
    private List<@URL @NotBlank String> images = new ArrayList<>();

    public BaseSpeaker() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getFrequencyResponse() {
        return frequencyResponse;
    }

    public void setFrequencyResponse(float frequencyResponse) {
        this.frequencyResponse = frequencyResponse;
    }

    public byte getNumberOfCoils() {
        return numberOfCoils;
    }

    public void setNumberOfCoils(byte numberOfCoils) {
        this.numberOfCoils = numberOfCoils;
    }

    public byte getImpedance() {
        return impedance;
    }

    public void setImpedance(byte impedance) {
        this.impedance = impedance;
    }

    public short getPowerHandling() {
        return powerHandling;
    }

    public void setPowerHandling(short powerHandling) {
        this.powerHandling = powerHandling;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
