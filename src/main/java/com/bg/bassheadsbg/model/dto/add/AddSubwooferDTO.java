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
public class AddSubwooferDTO {

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

    @Positive(message = "{coilHeight.positive}")
    @NotNull(message = "{coilHeight.positive}")
    @DecimalMax(value = "10", message = "{coilHeight.max10}")
    private Double coilHeight;

    @PositiveOrZero(message = "{coilLayers.positiveOrZero}")
    @NotNull(message = "{coilLayers.positiveOrZero}")
    @Max(value = 20, message = "{coilLayers.max20}")
    private Integer coilLayers;

    @Positive(message = "{magnetSize.positive}")
    @NotNull(message = "{magnetSize.positive}")
    @Max(value = 5000, message = "{magnetSize.max5000}")
    private Integer magnetSize;

    @PositiveOrZero(message = "{vas.positiveOrZero}")
    @NotNull(message = "{vas.positiveOrZero}")
    @DecimalMax(value = "1000", message = "{vas.max1000}")
    private Double vas;

    @Positive(message = "{xmax.positive}")
    @NotNull(message = "{xmax.positive}")
    @Max(value = 100, message = "{xmax.max100}")
    private Integer xmax;

    @PositiveOrZero(message = "{qms.positiveOrZero}")
    @NotNull(message = "{qms.positiveOrZero}")
    @DecimalMax(value = "30", message = "{qms.max30}")
    private Double qms;

    @PositiveOrZero(message = "{qes.positiveOrZero}")
    @NotNull(message = "{qes.positiveOrZero}")
    @DecimalMax(value = "10", message = "{qes.max10}")
    private Double qes;

    @PositiveOrZero(message = "{qts.positiveOrZero}")
    @NotNull(message = "{qts.positiveOrZero}")
    @DecimalMax(value = "10", message = "{qts.max10}")
    private Double qts;

    @PositiveOrZero(message = "{sd.positiveOrZero}")
    @NotNull(message = "{sd.positiveOrZero}")
    @DecimalMax(value = "2000", message = "{sd.max2000}")
    private Double sd;

    @PositiveOrZero(message = "{bl.positiveOrZero}")
    @NotNull(message = "{bl.positiveOrZero}")
    @DecimalMax(value = "150", message = "{bl.max150}")
    private Double bl;

    @Positive(message = "{mms.positive}")
    @NotNull(message = "{mms.positive}")
    @DecimalMax(value = "3000", message = "{mms.max3000}")
    private Double mms;

    @ValidUrlList(message = "{images.validUrlList}")
    private List<@URL(message = "{images.url}")
    @NotBlank(message = "{images.notBlank}")
            String> images = new ArrayList<>();

}