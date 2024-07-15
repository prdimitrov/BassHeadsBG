package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.util.ValueFormatterUtil;

import java.util.List;

public final class HighRangeDetailsDTO {
    private long id;
    private String brand;
    private String model;
    private Float sensitivity;
    private float size;
    private float frequencyResponse;
    private byte numberOfCoils;
    private byte impedance;
    private short powerHandling;
    private String material;
    private int frequencyRangeFrom;
    private int frequencyRangeTo;
    private String crossover;
    private List<String> images;
    private String imagesString;

    public HighRangeDetailsDTO() {}

    public long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getMaterial() {
        return material;
    }

    public int getFrequencyRangeFrom() {
        return frequencyRangeFrom;
    }

    public int getFrequencyRangeTo() {
        return frequencyRangeTo;
    }

    public String getCrossover() {
        return crossover;
    }

    public String getImagesString() {
        return imagesString;
    }

    public String formattedSensitivity() {
        if (ValueFormatterUtil.formatValue(sensitivity).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(sensitivity) + " dB (1W / 1m)";
    }

    public String formattedSize() {
        return ValueFormatterUtil.formatValue(size) + "\"";
    }

    public String formattedFrequencyResponse() {
        if (ValueFormatterUtil.formatValue(frequencyResponse).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(frequencyResponse) + " Hz";
    }

    public String formattedImpedance() {
        return impedance + " Ω";
    }

    public String formattedPowerHandling() {
        return powerHandling + " W";
    }

    public String formattedFrequencyRange() {
        return frequencyRangeFrom + " Hz - " + frequencyRangeTo + " Hz";
    }

    public List<String> getImages() {
        return images;
    }

    public Float getSensitivity() {
        return sensitivity;
    }

    public float getSize() {
        return size;
    }

    public float getFrequencyResponse() {
        return frequencyResponse;
    }

    public byte getNumberOfCoils() {
        return numberOfCoils;
    }

    public byte getImpedance() {
        return impedance;
    }

    public short getPowerHandling() {
        return powerHandling;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSensitivity(Float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setFrequencyResponse(float frequencyResponse) {
        this.frequencyResponse = frequencyResponse;
    }

    public void setNumberOfCoils(byte numberOfCoils) {
        this.numberOfCoils = numberOfCoils;
    }

    public void setImpedance(byte impedance) {
        this.impedance = impedance;
    }

    public void setPowerHandling(short powerHandling) {
        this.powerHandling = powerHandling;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setFrequencyRangeFrom(int frequencyRangeFrom) {
        this.frequencyRangeFrom = frequencyRangeFrom;
    }

    public void setFrequencyRangeTo(int frequencyRangeTo) {
        this.frequencyRangeTo = frequencyRangeTo;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public void setImagesString(String imagesString) {
        this.imagesString = imagesString;
    }

    public void setImages(List<String> images) {
        this.images = images;
        if (images != null && !images.isEmpty()) {
            this.imagesString = String.join(", ", images);
        } else {
            this.imagesString = "";
        }
    }
}