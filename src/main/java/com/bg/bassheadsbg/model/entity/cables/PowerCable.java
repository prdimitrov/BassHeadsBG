package com.bg.bassheadsbg.model.entity.cables;

import com.bg.bassheadsbg.model.entity.base.BaseEntity;
import com.bg.bassheadsbg.model.entity.images.PowerCableImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.enums.CableMaterial;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "{brand.notBlank}")
    @Size(min = 3, max = 20, message = "{brand.min3max20}")
    private String brand;

    @NotBlank(message = "{model.notBlank}")
    @Size(min = 3, max = 30, message = "{model.min3max30}")
    private String model;

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.positive}")
    @Max(value = 50000, message = "{price.max50000}")
    private int price;

    @NotNull(message = "{length.positiveOrZero}")
    @PositiveOrZero(message = "{length.positiveOrZero}")
    @DecimalMax(value = "100", message = "{length.max100}")
    private float length;

    @NotNull(message = "{cableMaterial.notNull}")
    @Enumerated(EnumType.STRING)
    private CableMaterial material;

    @NotNull
    @Positive(message = "{thickness.positive}")
    @DecimalMax(value = "500", message = "{thickness.max500}")
    private float thickness;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @Size(max = 500, message = "{description.max500}")
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

    public long getLikes() {
        return userLikes.size();
    }
}
