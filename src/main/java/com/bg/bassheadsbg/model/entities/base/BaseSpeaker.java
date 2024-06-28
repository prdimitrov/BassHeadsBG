package com.bg.bassheadsbg.model.entities.base;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class BaseSpeaker extends BaseEntity {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Positive
    @Min(value = 6, message = "This website is for true BassHeads, it's not headphones website, please enter a size larger than 6 inches.")
    private byte size;
    private float frequencyResponse;
    private byte numberOfCoils;
    private byte impedance;
    private short powerHandling;
    @URL(message = "Please, enter a valid URL.")
    private Set<String> imagesURL = new HashSet<>();

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

    public @URL Set<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(@URL Set<String> imagesURL) {
        this.imagesURL = imagesURL;
    }
}
