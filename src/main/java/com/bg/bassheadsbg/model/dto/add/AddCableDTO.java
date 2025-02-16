package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.CableMaterial;
import com.bg.bassheadsbg.model.enums.CableType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AddCableDTO {
    private long id;


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

    @NotNull
    @Enumerated(EnumType.STRING)
    private CableType cableType;

    private String thickness;

    private String description;

    @NotNull
    private List<MultipartFile> imageFiles = new ArrayList<>();
}
