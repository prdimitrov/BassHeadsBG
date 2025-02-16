package com.bg.bassheadsbg.model.entity.other;

import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.images.CableImage;
import com.bg.bassheadsbg.model.enums.CableMaterial;
import com.bg.bassheadsbg.model.enums.CableType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cables")
public class Cable extends BaseEntity {
    @NotBlank
    @Column(name = "brand")
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Positive
    private double length;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CableMaterial material;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cable_type", nullable = false)
    private CableType cableType;

    private short thickness;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "cable", orphanRemoval = true)
    private List<CableImage> imageFiles = new ArrayList<>();
}
