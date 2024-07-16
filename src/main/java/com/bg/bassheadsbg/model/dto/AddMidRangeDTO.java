package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;


public class AddMidRangeDTO {

    private long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
    @NotNull
    private float sensitivity;

    @Positive
    @NotNull
    @DecimalMax(value = "50", message = "Виждал ли си говорител по-голям от 50 инча?!")
    private float size;

    @Positive
    @NotNull
    private float frequencyResponse;

    @Positive
    @NotNull
    private byte numberOfCoils;

    @Positive
    @NotNull
    private float impedance;

    @Positive
    @NotNull
    private short powerHandling;

    @Positive
    @NotNull
    private int frequencyRangeFrom;

    @Positive
    @NotNull
    private int frequencyRangeTo;

    @ValidUrlList
    private List<@URL @NotBlank String> images = new ArrayList<>();

    public AddMidRangeDTO() {
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public float getImpedance() {
        return impedance;
    }

    public void setImpedance(float impedance) {
        this.impedance = impedance;
    }

    public short getPowerHandling() {
        return powerHandling;
    }

    public void setPowerHandling(short powerHandling) {
        this.powerHandling = powerHandling;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
