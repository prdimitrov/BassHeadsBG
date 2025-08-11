package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.CableMaterial;
import com.bg.bassheadsbg.model.interfaces.AddDeviceDTO;
import com.bg.bassheadsbg.validation.imagesValidator.NotEmptyImageFiles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddPowerCableDTO implements AddDeviceDTO {
    private long id;

    @NotBlank(message = "{brand.notBlank}")
    @Size(min = 3, max = 20, message = "{brand.min3max20}")
    private String brand;

    @NotBlank(message = "{model.notBlank}")
    @Size(min = 3, max = 30, message = "{model.min3max30}")
    private String model;

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private String price;

    @NotNull(message = "{length.positiveOrZero}")
    @PositiveOrZero(message = "{length.positiveOrZero}")
    @DecimalMax(value = "100", message = "{length.max100}")
    private String length;

    @NotNull(message = "{cableMaterial.notNull}")
    @Enumerated(EnumType.STRING)
    private CableMaterial material;

    @NotNull
    @Positive(message = "{thickness.positive}")
    @DecimalMax(value = "500", message = "{thickness.max500}")
    private String thickness;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @Size(max = 500, message = "{description.max500}")
    private String description;

    @NotEmptyImageFiles(message = "{imageFiles.notEmpty}")
    private List<MultipartFile> imageFiles;
}
