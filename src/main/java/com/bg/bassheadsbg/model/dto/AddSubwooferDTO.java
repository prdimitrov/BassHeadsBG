package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

public class AddSubwooferDTO {

    private long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
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
    private float coilHeight;

    @PositiveOrZero
    @NotNull
    private byte coilLayers;

    @Positive
    @NotNull
    private short magnetSize;

    @PositiveOrZero
    @NotNull
    private float vas;

    @Positive
    @NotNull
    private byte xmax;

    @PositiveOrZero
    @NotNull
    private float qms;

    @PositiveOrZero
    @NotNull
    private float qes;

    @PositiveOrZero
    @NotNull
    private float qts;

    @PositiveOrZero
    @NotNull
    private float sd;

    @PositiveOrZero
    @NotNull
    private float bl;

    @Positive
    @NotNull
    private float mms;

    @ValidUrlList
    private List<@URL @NotBlank String> images = new ArrayList<>();


    public AddSubwooferDTO() {
    }

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

    public float getCoilHeight() {
        return coilHeight;
    }

    public void setCoilHeight(float coilHeight) {
        this.coilHeight = coilHeight;
    }

    public byte getCoilLayers() {
        return coilLayers;
    }

    public void setCoilLayers(byte coilLayers) {
        this.coilLayers = coilLayers;
    }

    public short getMagnetSize() {
        return magnetSize;
    }

    public void setMagnetSize(short magnetSize) {
        this.magnetSize = magnetSize;
    }

    public float getVas() {
        return vas;
    }

    public void setVas(float vas) {
        this.vas = vas;
    }

    public byte getXmax() {
        return xmax;
    }

    public void setXmax(byte xmax) {
        this.xmax = xmax;
    }

    public float getQms() {
        return qms;
    }

    public void setQms(float qms) {
        this.qms = qms;
    }

    public float getQes() {
        return qes;
    }

    public void setQes(float qes) {
        this.qes = qes;
    }

    public float getQts() {
        return qts;
    }

    public void setQts(float qts) {
        this.qts = qts;
    }

    public float getSd() {
        return sd;
    }

    public void setSd(float sd) {
        this.sd = sd;
    }

    public float getBl() {
        return bl;
    }

    public void setBl(float bl) {
        this.bl = bl;
    }

    public float getMms() {
        return mms;
    }

    public void setMms(float mms) {
        this.mms = mms;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}