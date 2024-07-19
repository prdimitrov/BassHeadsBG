package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
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
    private float impedance;

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

}