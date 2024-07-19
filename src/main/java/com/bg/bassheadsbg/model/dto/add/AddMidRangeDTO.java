package com.bg.bassheadsbg.model.dto.add;

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
public class AddMidRangeDTO {

    private long id;

    @Positive
    private int price;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @PositiveOrZero
    @NotNull
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
    private int frequencyRangeFrom;

    @Positive
    @NotNull
    private int frequencyRangeTo;

    @ValidUrlList
    private List<@URL @NotBlank String> images = new ArrayList<>();

}
