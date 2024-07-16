package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.validation.ValidUrlList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class BaseAmplifier extends BaseEntity {
    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AmpClass amplifierClass;

    @PositiveOrZero
    @NotNull
    private byte impedance;

    @PositiveOrZero
    @NotNull
    private int power;

    @NotBlank
    private String highPassFilter;

    @NotBlank
    private String lowPassFilter;

    @NotBlank
    private String subsonicFilter;

    @NotNull
    private boolean remoteControl;

    @NotNull
    private boolean bassBoost;

    @NotBlank
    private String lowInputLevel;

    @NotBlank
    private String highInputLevel;

    @PositiveOrZero
    @NotNull
    private float distortion;

    @PositiveOrZero
    @NotNull
    private double currentDraw;

    @PositiveOrZero
    @NotNull
    private short fuseRating;

    //Amp dimensions!
    @PositiveOrZero
    @NotNull
    private Short height;
    @PositiveOrZero
    @NotNull
    private Short width;
    @PositiveOrZero
    @NotNull
    private Short length;

    @ValidUrlList
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "images")
    private List<String> images = new ArrayList<>();

    public BaseAmplifier() {
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

    public AmpClass getAmplifierClass() {
        return amplifierClass;
    }

    public void setAmplifierClass(AmpClass amplifierClass) {
        this.amplifierClass = amplifierClass;
    }

    public byte getImpedance() {
        return impedance;
    }

    public void setImpedance(byte impedance) {
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

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public Short getWidth() {
        return width;
    }

    public void setWidth(Short width) {
        this.width = width;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}