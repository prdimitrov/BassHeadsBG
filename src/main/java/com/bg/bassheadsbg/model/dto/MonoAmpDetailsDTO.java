package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.model.enums.AmpClass;

import java.util.List;
import java.util.Objects;

public final class MonoAmpDetailsDTO {
    private Long id;
    private String brand;
    private String model;
    private String amplifierClass;
    private byte impedance;
    private int power;
    private byte efficiency;
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
    private byte numberOfRca;
    private byte numberOfSpeakerOutputs;
    private List<String> images;

    public MonoAmpDetailsDTO() {
    }

    public Long id() {
        return id;
    }

    public String brand() {
        return brand;
    }

    public String model() {
        return model;
    }

    public String amplifierClass() {
        return amplifierClass;
    }

    public byte impedance() {
        return impedance;
    }

    public int power() {
        return power;
    }

    public byte efficiency() {
        return efficiency;
    }

    public String highPassFilter() {
        return highPassFilter;
    }

    public String lowPassFilter() {
        return lowPassFilter;
    }

    public String subsonicFilter() {
        return subsonicFilter;
    }

    public boolean remoteControl() {
        return remoteControl;
    }

    public boolean bassBoost() {
        return bassBoost;
    }

    public String lowInputLevel() {
        return lowInputLevel;
    }

    public String highInputLevel() {
        return highInputLevel;
    }

    public Float distortion() {
        return distortion;
    }

    public double currentDraw() {
        return currentDraw;
    }

    public short fuseRating() {
        return fuseRating;
    }

    public Short height() {
        return height;
    }

    public Short width() {
        return width;
    }

    public Short length() {
        return length;
    }

    public byte numberOfRca() {
        return numberOfRca;
    }

    public byte numberOfSpeakerOutputs() {
        return numberOfSpeakerOutputs;
    }




    @Override
    public String toString() {
        return "MonoAmpDetailsDTO[" +
                "id=" + id + ", " +
                "brand=" + brand + ", " +
                "model=" + model + ", " +
                "amplifierClass=" + amplifierClass + ", " +
                "impedance=" + impedance + ", " +
                "power=" + power + ", " +
                "efficiency=" + efficiency + ", " +
                "highPassFilter=" + highPassFilter + ", " +
                "lowPassFilter=" + lowPassFilter + ", " +
                "subsonicFilter=" + subsonicFilter + ", " +
                "remoteControl=" + remoteControl + ", " +
                "bassBoost=" + bassBoost + ", " +
                "lowInputLevel=" + lowInputLevel + ", " +
                "highInputLevel=" + highInputLevel + ", " +
                "distortion=" + distortion + ", " +
                "currentDraw=" + currentDraw + ", " +
                "fuseRating=" + fuseRating + ", " +
                "height=" + height + ", " +
                "width=" + width + ", " +
                "length=" + length + ", " +
                "numberOfRca=" + numberOfRca + ", " +
                "numberOfSpeakerOutputs=" + numberOfSpeakerOutputs + ']';
    }
}
