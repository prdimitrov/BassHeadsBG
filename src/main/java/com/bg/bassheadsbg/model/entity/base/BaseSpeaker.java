package com.bg.bassheadsbg.model.entity.base;

import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public abstract class BaseSpeaker extends BaseEntity {

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private int price;

    @NotBlank(message = "{brand.notBlank}")
    @Size(min = 3, max = 15, message = "{brand.min3max15}")
    private String brand;

    @NotBlank(message = "{model.notBlank}")
    @Size(min = 3, max = 30, message = "{model.min3max30}")
    private String model;

    @PositiveOrZero(message = "{sensitivity.positiveOrZero}")
    @NotNull(message = "{sensitivity.positiveOrZero}")
    @DecimalMax(value = "150", message = "{sensitivity.max150}")
    private float sensitivity;

    @NotNull(message = "{size.positive}")
    @Positive(message = "{size.positive}")
    @DecimalMax(value = "50", message = "{size.max50}")
    private float size;

    @Positive(message = "{frequencyResponse.positive}")
    @NotNull(message = "{frequencyResponse.positive}")
    @DecimalMax(value = "20000", message = "{frequencyResponse.max20000}")
    private float frequencyResponse;

    @Positive(message = "{numberOfCoils.positive}")
    @NotNull(message = "{numberOfCoils.positive}")
    @Max(value = 4, message = "{numberOfCoils.max4}")
    private byte numberOfCoils;

    @Positive(message = "{impedance.positive}")
    @NotNull(message = "{impedance.positive}")
    @DecimalMax(value = "16", message = "{impedance.max16}")
    private float impedance;

    @Positive(message = "{powerHandling.positive}")
    @NotNull(message = "{powerHandling.positive}")
    @Max(value = 32000, message = "{powerHandling.max32000}")
    private short powerHandling;

    @ValidUrlList(message = "{images.validUrlList}")
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "images")
    private List<@URL(message = "{images.url}") @NotBlank(message = "{images.notBlank}")
            String> images = new ArrayList<>();

}
