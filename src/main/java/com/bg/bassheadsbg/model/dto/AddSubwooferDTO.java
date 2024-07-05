package com.bg.bassheadsbg.model.dto;

import jakarta.validation.constraints.*;

public class AddSubwooferDTO {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Positive
    private Float sensitivity;

    @Positive
    @NotNull
    @Min(value = 6, message = "This website is for true BassHeads, it's not headphones website, please enter a size larger than 6 inches.")
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

    @Positive
    @NotNull
    private float coilHeight;

    @Positive
    private Byte coilLayers;

    @Positive
    @NotNull
    private short magnetSize;

    @Positive
    private Float vas;

    @Positive
    @NotNull
    private byte xmax;

    @Positive
    private Float qms;

    @Positive
    private Float qes;

    @Positive
    private Float qts;

    @Positive
    private Float sd;

    @Positive
    private Float bl;

    @Positive
    @NotNull
    private float mms;

    public AddSubwooferDTO() {
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

    @Override
    public String toString() {
        return "AddSubwooferDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", sensitivity=" + sensitivity +
                ", size=" + size +
                ", frequencyResponse=" + frequencyResponse +
                ", numberOfCoils=" + numberOfCoils +
                ", impedance=" + impedance +
                ", powerHandling=" + powerHandling +
                ", coilHeight=" + coilHeight +
                ", coilLayers=" + coilLayers +
                ", magnetSize=" + magnetSize +
                ", vas=" + vas +
                ", xmax=" + xmax +
                ", qms=" + qms +
                ", qes=" + qes +
                ", qts=" + qts +
                ", sd=" + sd +
                ", bl=" + bl +
                ", mms=" + mms +
                '}';
    }
}