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

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private String price;

    @NotBlank(message = "{brand.notBlank}")
    @Size(min = 3, max = 15, message = "{brand.min3max15}")
    private String brand;

    @NotBlank
    @Size(min = 3, max = 30, message = "{model.min3max30}")
    private String model;

    @PositiveOrZero(message = "{sensitivity.positiveOrZero}")
    @NotNull(message = "{sensitivity.positiveOrZero}")
    @DecimalMax(value = "50", message = "{sensitivity.max50}")
    private String sensitivity;

    @NotNull(message = "{size.positive}")
    @Positive(message = "{size.positive}")
    @DecimalMax(value = "50", message = "{size.max50}")
    private String size;

    @Positive(message = "{frequencyResponse.positive}")
    @NotNull(message = "{frequencyResponse.positive}")
    @DecimalMax(value = "20000", message = "{frequencyResponse.max20000}")
    private String frequencyResponse;

    @Positive(message = "{numberOfCoils.positive}")
    @NotNull(message = "{numberOfCoils.positive}")
    @Max(value = 4, message = "{numberOfCoils.max4}")
    private String numberOfCoils;

    @Positive(message = "{impedance.positive}")
    @NotNull(message = "{impedance.positive}")
    @DecimalMax(value = "16", message = "{impedance.max16}")
    private String impedance;

    @Positive(message = "{powerHandling.positive}")
    @NotNull(message = "{powerHandling.positive}")
    @Max(value = 32000, message = "{powerHandling.max32000}")
    private String powerHandling;

    @PositiveOrZero(message = "{frequencyRangeFrom.positiveOrZero}")
    @NotNull(message = "{frequencyRangeFrom.positiveOrZero}")
    @Max(value = 40000, message = "{frequencyRangeFrom.max40000}")
    private String frequencyRangeFrom;

    @PositiveOrZero(message = "{frequencyRangeTo.positiveOrZero}")
    @NotNull(message = "{frequencyRangeTo.positiveOrZero}")
    @Max(value = 80000, message = "{frequencyRangeTo.max80000}")
    private String frequencyRangeTo;

    @ValidUrlList(message = "{images.validUrlList}")
    private List<@URL(message = "{images.url}")
    @NotBlank(message = "{images.notBlank}")
            String> images = new ArrayList<>();

}
