package com.bg.bassheadsbg.model.entities.base;

import com.bg.bassheadsbg.model.entities.enums.AmpClass;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseAmplifier extends BaseEntity {
    private String brand;
    private String model;
    @Enumerated(EnumType.STRING)
    private AmpClass amplifierClass;
    private byte impedance;
    private int power;
    private byte efficiency;
    private short highPassFilter;
    private byte lowPassFilter;
    private boolean subsonicFilter;
    private boolean remoteControl;
    private boolean bassBoost;
    private float lowInputLevel;
    private float highInputLevel;
    private double distortion;
    private double currentDraw;

    private short fuseRating;

    //Amp dimensions!
    private short height;
    private short width;
    private short length;

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

    public short getHighPassFilter() {
        return highPassFilter;
    }

    public void setHighPassFilter(short highPassFilter) {
        this.highPassFilter = highPassFilter;
    }

    public byte getLowPassFilter() {
        return lowPassFilter;
    }

    public void setLowPassFilter(byte lowPassFilter) {
        this.lowPassFilter = lowPassFilter;
    }

    public boolean isSubsonicFilter() {
        return subsonicFilter;
    }

    public void setSubsonicFilter(boolean subsonicFilter) {
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

    public float getLowInputLevel() {
        return lowInputLevel;
    }

    public void setLowInputLevel(float lowInputLevel) {
        this.lowInputLevel = lowInputLevel;
    }

    public float getHighInputLevel() {
        return highInputLevel;
    }

    public void setHighInputLevel(float highInputLevel) {
        this.highInputLevel = highInputLevel;
    }

    public double getDistortion() {
        return distortion;
    }

    public void setDistortion(double distortion) {
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
}