package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.validation.ValidUrlSet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class BaseAmplifier extends BaseEntity {
    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AmpClass amplifierClass;

    @Positive
    @NotNull
    private byte impedance;

    @Positive
    @NotNull
    private int power;

    @Positive
    @NotNull
    private byte efficiency;


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

    @Positive
    private Float distortion;

    @Positive
    @NotNull
    private double currentDraw;

    @Positive
    @NotNull
    private short fuseRating;

    //Amp dimensions!
    @Positive
    private Short height;
    @Positive
    private Short width;
    @Positive
    private Short length;

    @ValidUrlSet
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "images")
    private Set<String> images = new HashSet<>();

    public BaseAmplifier() {
        super();
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

    public byte getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(byte efficiency) {
        this.efficiency = efficiency;
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

    public Float getDistortion() {
        return distortion;
    }

    public void setDistortion(Float distortion) {
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

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }
}