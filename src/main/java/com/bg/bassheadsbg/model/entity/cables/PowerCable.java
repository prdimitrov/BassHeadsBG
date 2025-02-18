package com.bg.bassheadsbg.model.entity.cables;

import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.images.PowerCableImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.enums.CableMaterial;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
@Table(name = "power_cables")
public class PowerCable extends BaseEntity {
    //TODO: Validations!!!!!
    @NotBlank
    @Column(name = "brand")
    private String brand;

    @NotBlank
    private String model;

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private int price;

    @NotNull
    @Positive
    private float length;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CableMaterial material;

    private float thickness;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "powerCable", orphanRemoval = true)
    private List<PowerCableImage> imageFiles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "power_cables_user_likes",
            joinColumns = @JoinColumn(name = "power_cable_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"power_cable_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();
}
