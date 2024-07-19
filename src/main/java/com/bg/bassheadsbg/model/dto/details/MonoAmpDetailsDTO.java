package com.bg.bassheadsbg.model.dto.details;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class MonoAmpDetailsDTO {
    private Long id;
    private int price;
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

    public void setImages(List<String> images) {
        this.images = images;
        if (images != null && !images.isEmpty()) {
            this.imagesString = String.join(", ", images);
        } else {
            this.imagesString = "";
        }
    }
}
