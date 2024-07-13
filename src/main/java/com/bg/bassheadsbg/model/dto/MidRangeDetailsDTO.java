package com.bg.bassheadsbg.model.dto;

import java.util.List;

public final class MidRangeDetailsDTO {
    private long id;
    private String brand;
    private String model;
    private Float sensitivity;
    private float size;
    private float frequencyResponse;
    private byte numberOfCoils;
    private byte impedance;
    private short powerHandling;
    private int frequencyRangeFrom;
    private int frequencyRangeTo;
    private List<String> images;
    private String imagesString;

    public MidRangeDetailsDTO() {}

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

    // Helper methods for formatting
    public String formattedSensitivity() {
        return formatNumber(sensitivity) + " dB (1W / 1m)";
    }

    public String formattedSize() {
        return formatNumber(size) + " \"";
    }

    public String formattedFrequencyResponse() {
        return formatNumber(frequencyResponse) + " Hz";
    }

    public String formattedNumberOfCoils() {
        return Byte.toString(numberOfCoils);
    }

    public String formattedImpedance() {
        return impedance + " Ω";
    }

    public String formattedPowerHandling() {
        return powerHandling + " W";
    }

    public String formattedFrequencyRange() {
        return "From " + frequencyRangeFrom + " Hz - " + frequencyRangeTo + " Hz";
    }

    // Utility method to format numbers
    private String formatNumber(Number number) {
        if (number == null) {
            return "";
        }
        if (number.doubleValue() % 1 == 0) {
            return String.valueOf(number.intValue());
        } else {
            return String.valueOf(number);
        }
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
        if (images != null && !images.isEmpty()) {
            this.imagesString = String.join(", ", images);
        } else {
            this.imagesString = "";
        }
    }

    public String getImagesString() {
        return imagesString;
    }

    public void setImagesString(String imagesString) {
        this.imagesString = imagesString;
    }
}