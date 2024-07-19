package com.bg.bassheadsbg.model.dto;

import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AddSubwooferDTO {

    private long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
    private float sensitivity;

    @Positive
    @NotNull
    @DecimalMax(value = "50", message = "Виждал ли си говорител по-голям от 50 инча?!")
    private float size;

    @Positive
    @NotNull
    private float frequencyResponse;

    @Positive
    @NotNull
    private byte numberOfCoils;

    @Positive
    @NotNull
    private float impedance;

    @Positive
    @NotNull
    private short powerHandling;

    @Positive
    @NotNull
    private float coilHeight;

    @PositiveOrZero
    @NotNull
    private byte coilLayers;

    @Positive
    @NotNull
    private short magnetSize;

    @PositiveOrZero
    @NotNull
    private float vas;

    @Positive
    @NotNull
    private byte xmax;

    @PositiveOrZero
    @NotNull
    private float qms;

    @PositiveOrZero
    @NotNull
    private float qes;

    @PositiveOrZero
    @NotNull
    private float qts;

    @PositiveOrZero
    @NotNull
    private float sd;

    @PositiveOrZero
    @NotNull
    private float bl;

    @Positive
    @NotNull
    private float mms;

    @ValidUrlList
    private List<@URL @NotBlank String> images = new ArrayList<>();

}