package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.validation.ValidUrlList;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

public class AddHighRangeDTO {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
    private Float sensitivity;

    @Positive
    @NotNull
    private float size;

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

    @NotBlank
    private String material;

    @Positive
    @NotNull
    private int frequencyRangeFrom;

    @Positive
    @NotNull
    private int frequencyRangeTo;

    @NotBlank
    private String crossover;

    @ValidUrlList
    private List<@URL @NotBlank String> images = new ArrayList<>();

    // Getters and Setters

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

    public Float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Float sensitivity) {
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getFrequencyRangeFrom() {
        return frequencyRangeFrom;
    }

    public void setFrequencyRangeFrom(int frequencyRangeFrom) {
        this.frequencyRangeFrom = frequencyRangeFrom;
    }

    public int getFrequencyRangeTo() {
        return frequencyRangeTo;
    }

    public void setFrequencyRangeTo(int frequencyRangeTo) {
        this.frequencyRangeTo = frequencyRangeTo;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
