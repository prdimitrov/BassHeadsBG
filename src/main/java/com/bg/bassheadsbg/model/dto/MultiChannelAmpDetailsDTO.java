package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.util.ValueFormatterUtil;

import java.util.List;

public final class MultiChannelAmpDetailsDTO {
    private Long id;
    private String brand;
    private String model;
    private String amplifierClass;
    private byte impedance;
    private int power;
    private String highPassFilter;
    private String lowPassFilter;
    private String subsonicFilter;
    private boolean remoteControl;
    private boolean bassBoost;
    private String lowInputLevel;
    private String highInputLevel;
    private Float distortion;
    private double currentDraw;
    private short fuseRating;
    private Short height;
    private Short width;
    private Short length;
    private byte numberOfChannels;
    private List<String> images;
    private String imagesString;

    public MultiChannelAmpDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getAmplifierClass() {
        return amplifierClass;
    }

    public String getImpedance() {
        return ValueFormatterUtil.formatValue(impedance) + " Ω";
    }

    public String getPower() {
        return ValueFormatterUtil.formatValue(power) + " W";
    }

    public String getHighPassFilter() {
        return ValueFormatterUtil.formatValue(highPassFilter);
    }

    public String getLowPassFilter() {
        return ValueFormatterUtil.formatValue(lowPassFilter);
    }

    public String getSubsonicFilter() {
        return ValueFormatterUtil.formatValue(subsonicFilter);
    }

    public String isRemoteControl() {
        return ValueFormatterUtil.formatValue(isRemoteControl());
    }

    public String isBassBoost() {
        return ValueFormatterUtil.formatValue(isBassBoost());
    }

    public String getLowInputLevel() {
        return ValueFormatterUtil.formatValue(lowInputLevel);
    }

    public String getHighInputLevel() {
        return ValueFormatterUtil.formatValue(highInputLevel);
    }

    public String getDistortion() {
        return ">" + ValueFormatterUtil.formatValue(distortion) + " %";
    }

    public String getCurrentDraw() {
        return ValueFormatterUtil.formatValue(currentDraw) + " A";
    }

    public String getFuseRating() {
        return ValueFormatterUtil.formatValue(fuseRating) + " A";
    }

    public String getNumberOfChannels() {
        return ValueFormatterUtil.formatValue(numberOfChannels);
    }

    public String getHeight() {
        if (ValueFormatterUtil.formatValue(height).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(height) + " mm";
    }

    public String getWidth() {
        if (ValueFormatterUtil.formatValue(width).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(width) + " mm";
    }

    public String getLength() {
        if (ValueFormatterUtil.formatValue(length).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(length) + " mm";
    }

    public List<String> getImages() {
        return images;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAmplifierClass(String amplifierClass) {
        this.amplifierClass = amplifierClass;
    }

    public void setImpedance(byte impedance) {
        this.impedance = impedance;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setHighPassFilter(String highPassFilter) {
        this.highPassFilter = highPassFilter;
    }

    public void setLowPassFilter(String lowPassFilter) {
        this.lowPassFilter = lowPassFilter;
    }

    public void setSubsonicFilter(String subsonicFilter) {
        this.subsonicFilter = subsonicFilter;
    }

    public void setRemoteControl(boolean remoteControl) {
        this.remoteControl = remoteControl;
    }

    public void setBassBoost(boolean bassBoost) {
        this.bassBoost = bassBoost;
    }

    public void setLowInputLevel(String lowInputLevel) {
        this.lowInputLevel = lowInputLevel;
    }

    public void setHighInputLevel(String highInputLevel) {
        this.highInputLevel = highInputLevel;
    }

    public void setDistortion(Float distortion) {
        this.distortion = distortion;
    }

    public void setCurrentDraw(double currentDraw) {
        this.currentDraw = currentDraw;
    }

    public void setFuseRating(short fuseRating) {
        this.fuseRating = fuseRating;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public void setWidth(Short width) {
        this.width = width;
    }

    public void setLength(Short length) {
        this.length = length;
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

    public void setNumberOfChannels(byte numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }
}
