package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.validation.imagesUrlValidator.ValidUrlList;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AddMonoAmpDTO {

    private long id;

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private String price;

    @NotBlank(message = "{brand.notBlank}")
    @Size(min = 3, max = 15, message = "{brand.min3max15}")
    private String brand;

    @NotBlank(message = "{model.notBlank}")
    @Size(min = 3, max = 30, message = "{model.min3max30}")
    private String model;

    @NotNull(message = "{amplifierClass.notNull}")
    @Enumerated(EnumType.STRING)
    private AmpClass amplifierClass;

    @Positive(message = "{impedance.positive}")
    @NotNull(message = "{impedance.positive}")
    @DecimalMax(value = "16", message = "{impedance.max16}")
    private String impedance;

    @Positive(message = "{power.positive}")
    @NotNull(message = "{power.positive}")
    @Max(value = 300000, message = "{power.max300000}")
    private String power;

    @Size(max = 100, message = "{highPassFilter.sizeMax100}")
    private String highPassFilter;

    @Size(max = 100, message = "{lowPassFilter.sizeMax100}")
    private String lowPassFilter;

    @Size(max = 100, message = "{subsonicFilter.sizeMax100}")
    private String subsonicFilter;

    @NotNull(message = "{remoteControl.booleanNotNull}")
    private boolean remoteControl;

    @NotNull(message = "{bassBoost.booleanNotNull}")
    private boolean bassBoost;

    @NotBlank(message = "{lowInputLevel.notBlank}")
    @Size(max = 100, message = "{lowInputLevel.max100}")
    private String lowInputLevel;

    @NotBlank(message = "{highInputLevel.notBlank}")
    @Size(max = 100, message = "{highInputLevel.max100}")
    private String highInputLevel;

    @PositiveOrZero(message = "{distortion.positiveOrZero}")
    @NotNull(message = "{distortion.positiveOrZero}")
    @DecimalMax(value = "50", message = "{distortion.max50}")
    private String distortion;

    @Positive(message = "{currentDraw.positive}")
    @NotNull(message = "{currentDraw.positive}")
    @DecimalMax(value = "20000", message = "{currentDraw.max20000}")
    private String currentDraw;

    @Positive(message = "{fuseRating.positive}")
    @NotNull(message = "{fuseRating.positive}")
    @Max(value = 20000, message = "{fuseRating.max20000}")
    private String fuseRating;

    @NotNull(message = "{numberOfRca.positive}")
    @Positive(message = "{numberOfRca.positive}")
    @Max(value = 4, message = "{numberOfRca.max4}")
    private String numberOfRca;

    @NotNull(message = "{numberOfSpeakerOutputs.positive}")
    @Positive(message = "{numberOfSpeakerOutputs.positive}")
    @Max(value = 16, message = "numberOfSpeakerOutputs.max16")
    private String numberOfSpeakerOutputs;

    @PositiveOrZero(message = "{height.positiveOrZero}")
    @NotNull(message = "{height.positiveOrZero}")
    @Max(value = 30000, message = "{height.max30000}")
    private String height;

    @PositiveOrZero(message = "{width.positiveOrZero}")
    @NotNull(message = "{width.positiveOrZero}")
    @Max(value = 30000, message = "{width.max30000}")
    private String width;

    @PositiveOrZero(message = "{length.positiveOrZero}")
    @NotNull(message = "{length.positiveOrZero}")
    @Max(value = 30000, message = "{length.max30000}")
    private String length;

    @ValidUrlList(message = "{images.validUrlList}")
    private List<@URL(message = "{images.url}")
    @NotBlank(message = "{images.notBlank}")
            String> images = new ArrayList<>();

}
