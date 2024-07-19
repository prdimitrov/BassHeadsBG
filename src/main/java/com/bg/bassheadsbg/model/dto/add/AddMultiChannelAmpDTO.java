package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddMultiChannelAmpDTO {

    private long id;

    @Positive
    private int price;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AmpClass amplifierClass;

    @Positive
    @NotNull
    private float impedance;

    @Positive
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

    @Positive
    @NotNull
    private double currentDraw;

    @Positive
    @NotNull
    private short fuseRating;

    @Positive
    @NotNull
    private byte numberOfChannels;

    @PositiveOrZero
    @NotNull
    private short height;

    @PositiveOrZero
    @NotNull
    private short width;

    @PositiveOrZero
    @NotNull
    private short length;

    @ValidUrlList
    private List<@URL @NotBlank String> images = new ArrayList<>();

}
