package com.bg.bassheadsbg.model.details;

import java.util.List;

public final class MonoAmpDetailsDTO {
    private Long id;
    private String brand;
    private String model;
    private String amplifierClass;
    private float impedance;
    private int power;
    private String highPassFilter;
    private String lowPassFilter;
    private String subsonicFilter;
    private boolean remoteControl;
    private boolean bassBoost;
    private String lowInputLevel;
    private String highInputLevel;
    private float distortion;
    private double currentDraw;
    private short fuseRating;
    private short height;
    private short width;
    private short length;
    private byte numberOfRca;
    private byte numberOfSpeakerOutputs;
    private List<String> images;
    private String imagesString;

    public MonoAmpDetailsDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAmplifierClass() {
        return amplifierClass;
    }

    public void setAmplifierClass(String amplifierClass) {
        this.amplifierClass = amplifierClass;
    }

    public float getImpedance() {
        return impedance;
    }

    public void setImpedance(float impedance) {
        this.impedance = impedance;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getHighPassFilter() {
        return highPassFilter;
    }

    public void setHighPassFilter(String highPassFilter) {
        this.highPassFilter = highPassFilter;
    }

    public String getLowPassFilter() {
        return lowPassFilter;
    }

    public void setLowPassFilter(String lowPassFilter) {
        this.lowPassFilter = lowPassFilter;
    }

    public String getSubsonicFilter() {
        return subsonicFilter;
    }

    public void setSubsonicFilter(String subsonicFilter) {
        this.subsonicFilter = subsonicFilter;
    }

    public boolean isRemoteControl() {
        return remoteControl;
    }

    public void setRemoteControl(boolean remoteControl) {
        this.remoteControl = remoteControl;
    }

    public boolean isBassBoost() {
        return bassBoost;
    }

    public void setBassBoost(boolean bassBoost) {
        this.bassBoost = bassBoost;
    }

    public String getLowInputLevel() {
        return lowInputLevel;
    }

    public void setLowInputLevel(String lowInputLevel) {
        this.lowInputLevel = lowInputLevel;
    }

    public String getHighInputLevel() {
        return highInputLevel;
    }

    public void setHighInputLevel(String highInputLevel) {
        this.highInputLevel = highInputLevel;
    }

    public float getDistortion() {
        return distortion;
    }

    public void setDistortion(float distortion) {
        this.distortion = distortion;
    }

    public double getCurrentDraw() {
        return currentDraw;
    }

    public void setCurrentDraw(double currentDraw) {
        this.currentDraw = currentDraw;
    }

    public short getFuseRating() {
        return fuseRating;
    }

    public void setFuseRating(short fuseRating) {
        this.fuseRating = fuseRating;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public short getWidth() {
        return width;
    }

    public void setWidth(short width) {
        this.width = width;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte getNumberOfRca() {
        return numberOfRca;
    }

    public void setNumberOfRca(byte numberOfRca) {
        this.numberOfRca = numberOfRca;
    }

    public byte getNumberOfSpeakerOutputs() {
        return numberOfSpeakerOutputs;
    }

    public void setNumberOfSpeakerOutputs(byte numberOfSpeakerOutputs) {
        this.numberOfSpeakerOutputs = numberOfSpeakerOutputs;
    }

    public List<String> getImages() {
        return images;
    }

    public String getImagesString() {
        return imagesString;
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
