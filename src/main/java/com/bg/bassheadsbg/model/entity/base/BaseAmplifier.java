package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseAmplifier extends BaseEntity {

    @Positive
    private int price;

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

    private String highPassFilter;

    private String lowPassFilter;

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
    private List<@URL @NotBlank String> images = new ArrayList<>();

}