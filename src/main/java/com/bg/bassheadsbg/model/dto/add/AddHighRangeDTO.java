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
public class AddHighRangeDTO {

    private long id;

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private Integer price;

    @NotBlank(message = "{brand.notBlank}")
    @Size(min = 3, max = 15, message = "{brand.min3max15}")
    private String brand;

    @NotBlank
    @Size(min = 3, max = 30, message = "{model.min3max30}")
    private String model;

    @PositiveOrZero(message = "{sensitivity.positiveOrZero}")
    @NotNull(message = "{sensitivity.positiveOrZero}")
    @DecimalMax(value = "50", message = "{sensitivity.max50}")
    private Double sensitivity;

    @NotNull(message = "{size.positive}")
    @Positive(message = "{size.positive}")
    @DecimalMax(value = "50", message = "{size.max50}")
    private Double size;

    @Positive(message = "{frequencyResponse.positive}")
    @NotNull(message = "{frequencyResponse.positive}")
    @DecimalMax(value = "20000", message = "{frequencyResponse.max20000}")
    private Double frequencyResponse;

    @Positive(message = "{numberOfCoils.positive}")
    @NotNull(message = "{numberOfCoils.positive}")
    @Max(value = 4, message = "{numberOfCoils.max4}")
    private Integer numberOfCoils;

    @Positive(message = "{impedance.positive}")
    @NotNull(message = "{impedance.positive}")
    @DecimalMax(value = "16", message = "{impedance.max16}")
    private Double impedance;

    @Positive(message = "{powerHandling.positive}")
    @NotNull(message = "{powerHandling.positive}")
    @Max(value = 32000, message = "{powerHandling.max32000}")
    private Integer powerHandling;

    @NotBlank(message = "{material.notBlank}")
    private String material;

    @Positive(message = "{frequencyRangeFrom.positive}")
    @NotNull(message = "{frequencyRangeFrom.positive}")
    @Max(value = 40000, message = "{frequencyRangeFrom.max40000}")
    private Integer frequencyRangeFrom;

    @Positive(message = "{frequencyRangeTo.positive}")
    @NotNull(message = "{frequencyRangeTo.positive}")
    @Max(value = 80000, message = "{frequencyRangeTo.max80000}")
    private Integer frequencyRangeTo;

    @NotBlank(message = "{crossover.notBlank}")
    private String crossover;

    @ValidUrlList(message = "{images.validUrlList}")
    private List<@URL(message = "{images.url}")
                 @NotBlank(message = "{images.notBlank}")
            String> images = new ArrayList<>();

}
