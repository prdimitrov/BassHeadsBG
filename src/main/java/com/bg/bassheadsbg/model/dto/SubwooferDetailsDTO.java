package com.bg.bassheadsbg.model.dto;

import java.util.List;
import java.util.Objects;

public final class SubwooferDetailsDTO {
    private Long id;
    private String brand;
    private String model;
    private Float sensitivity;
    private float size;
    private float frequencyResponse;
    private byte numberOfCoils;
    private byte impedance;
    private short powerHandling;
    private float coilHeight;
    private Byte coilLayers;
    private short magnetSize;
    private Float vas;
    private byte xmax;
    private Float qms;
    private Float qes;
    private Float qts;
    private Float sd;
    private Float bl;
    private float mms;
    private List<String> images;
    private String imagesString;

    public SubwooferDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public float getCoilHeight() {
        return coilHeight;
    }

    public void setCoilHeight(float coilHeight) {
        this.coilHeight = coilHeight;
    }

    public Byte getCoilLayers() {
        return coilLayers;
    }

    public void setCoilLayers(Byte coilLayers) {
        this.coilLayers = coilLayers;
    }

    public short getMagnetSize() {
        return magnetSize;
    }

    public void setMagnetSize(short magnetSize) {
        this.magnetSize = magnetSize;
    }

    public Float getVas() {
        return vas;
    }

    public void setVas(Float vas) {
        this.vas = vas;
    }

    public byte getXmax() {
        return xmax;
    }

    public void setXmax(byte xmax) {
        this.xmax = xmax;
    }

    public Float getQms() {
        return qms;
    }

    public void setQms(Float qms) {
        this.qms = qms;
    }

    public Float getQes() {
        return qes;
    }

    public void setQes(Float qes) {
        this.qes = qes;
    }

    public Float getQts() {
        return qts;
    }

    public void setQts(Float qts) {
        this.qts = qts;
    }

    public Float getSd() {
        return sd;
    }

    public void setSd(Float sd) {
        this.sd = sd;
    }

    public Float getBl() {
        return bl;
    }

    public void setBl(Float bl) {
        this.bl = bl;
    }

    public float getMms() {
        return mms;
    }

    public void setMms(float mms) {
        this.mms = mms;
    }

    // Helper methods for formatting
    public String formattedSensitivity(Number number) {
        return formatNumber(sensitivity);
    }

    public String formattedSensitivity() {
        return formatNumber(sensitivity) + "dB (1W / 1m)";
    }

    public String formattedSize() {
        return formatNumber(size) + "\"";
    }

    public String formattedFrequencyResponse() {
        return formatNumber(frequencyResponse) + "Hz";
    }

    public String formattedNumberOfCoils() {
        return Byte.toString(numberOfCoils);
    }

    public String formattedImpedance() {
        return impedance + "Ω";
    }

    public String formattedPowerHandling() {
        return powerHandling + "W";
    }

    public String formattedCoilHeight() {
        return formatNumber(coilHeight) + "\"";
    }

    public String formattedCoilLayers() {
        return coilLayers == null ? "" : Byte.toString(coilLayers);
    }

    public String formattedMagnetSize() {
        return magnetSize + "oz";
    }

    public String formattedVas() {
        return formatNumber(vas) + "L";
    }

    public String formattedXmax() {
        return xmax + "mm";
    }

    public String formattedQms() {
        return formatNumber(qms);
    }
    public String formattedQes() {
        return formatNumber(qes);
    }
    public String formattedQts() {
        return formatNumber(qts);
    }

    public String formattedSd() {
        return formatNumber(sd) + " mm²";
    }

    public String formattedBl() {
        return formatNumber(bl) + " T/m";
    }

    public String formattedMms() {
        return formatNumber(mms) + "g";
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

    @Override
    public String toString() {
        return "SubwooferDetailsDTO[" +
                "id=" + id + ", " +
                "brand=" + brand + ", " +
                "model=" + model + ", " +
                "sensitivity=" + sensitivity + ", " +
                "size=" + size + ", " +
                "frequencyResponse=" + frequencyResponse + ", " +
                "numberOfCoils=" + numberOfCoils + ", " +
                "impedance=" + impedance + ", " +
                "powerHandling=" + powerHandling + ", " +
                "coilHeight=" + coilHeight + ", " +
                "coilLayers=" + coilLayers + ", " +
                "magnetSize=" + magnetSize + ", " +
                "vas=" + vas + ", " +
                "xmax=" + xmax + ", " +
                "qms=" + qms + ", " +
                "qes=" + qes + ", " +
                "qts=" + qts + ", " +
                "sd=" + sd + ", " +
                "bl=" + bl + ", " +
                "mms=" + mms + ']';
    }
}