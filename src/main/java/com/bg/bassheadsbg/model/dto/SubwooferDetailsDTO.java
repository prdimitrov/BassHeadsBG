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


    public Long id() {
        return id;
    }

    public String brand() {
        return brand;
    }

    public String model() {
        return model;
    }

    public Float sensitivity() {
        return sensitivity;
    }

    public float size() {
        return size;
    }

    public float frequencyResponse() {
        return frequencyResponse;
    }

    public byte numberOfCoils() {
        return numberOfCoils;
    }

    public byte impedance() {
        return impedance;
    }

    public short powerHandling() {
        return powerHandling;
    }

    public float coilHeight() {
        return coilHeight;
    }

    public Byte coilLayers() {
        return coilLayers;
    }

    public short magnetSize() {
        return magnetSize;
    }

    public Float vas() {
        return vas;
    }

    public byte xmax() {
        return xmax;
    }

    public Float qms() {
        return qms;
    }

    public Float qes() {
        return qes;
    }

    public Float qts() {
        return qts;
    }

    public Float sd() {
        return sd;
    }

    public Float bl() {
        return bl;
    }

    public float mms() {
        return mms;
    }

    // Helper methods for formatting
    public String formattedSensitivity(Number number) {
        return formatNumber(sensitivity);
    }

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

    public String formattedCoilHeight() {
        return coilHeight + " \"";
    }

    public String formattedCoilLayers() {
        return Byte.toString(coilLayers);
    }

    public String formattedMagnetSize() {
        return magnetSize + " oz";
    }

    public String formattedVas() {
        return formatNumber(vas) + " L";
    }

    public String formattedXmax() {
        return xmax + " mm";
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
        return formatNumber(mms) + " 687g";
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