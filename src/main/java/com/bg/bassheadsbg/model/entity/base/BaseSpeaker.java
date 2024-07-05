package com.bg.bassheadsbg.model.entity.base;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@MappedSuperclass
public abstract class BaseSpeaker extends BaseEntity {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;

    @Positive
    @NotNull
    private byte sensitivity;
    @Positive
    @NotNull
    @Min(value = 6, message = "This website is for true BassHeads, it's not headphones website, \nplease enter a size larger than 6 inches.")
    private byte size;
    @Positive
    @NotNull
    private float frequencyResponse;
    @Positive
    @NotNull
    private byte numberOfCoils;
    @Positive
    @NotNull
    private byte impedance;
    @Positive
    @NotNull
    private short powerHandling;

    public BaseSpeaker() {
        super();
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

    public byte getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(byte sensitivity) {
        this.sensitivity = sensitivity;
    }

    public byte getSize() {
        return size;
    }

    public void setSize(byte size) {
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

}
