package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.CableMaterial;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddPowerCableDTO {
    private long id;

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private String price;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Positive
    private String length;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CableMaterial material;

    private String thickness;

    private String description;

    @NotNull
    private List<MultipartFile> imageFiles = new ArrayList<>();
}
