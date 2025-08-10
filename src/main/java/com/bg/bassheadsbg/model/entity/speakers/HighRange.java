package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.DeviceEntity;
import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import com.bg.bassheadsbg.model.entity.images.HighRangeImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker implements DeviceEntity<HighRangeImage> {
    @NotBlank(message = "{material.notBlank}")
    @Size(max = 100, message = "{material.max100}")
    @Column(nullable = false)
    private String material;

    @PositiveOrZero(message = "{frequencyRangeFrom.positiveOrZero}")
    @NotNull(message = "{frequencyRangeFrom.positiveOrZero}")
    @Max(value = 40000, message = "{frequencyRangeFrom.max40000}")
    private int frequencyRangeFrom;

    @PositiveOrZero(message = "{frequencyRangeTo.positiveOrZero}")
    @NotNull(message = "{frequencyRangeTo.positiveOrZero}")
    @Max(value = 80000, message = "{frequencyRangeTo.max80000}")
    private int frequencyRangeTo;

    @NotBlank(message = "{crossover.notBlank}")
    @Size(max = 100, message = "{crossover.max100}")
    @Column(nullable = false)
    private String crossover;

    @OneToMany(mappedBy = "device", orphanRemoval = true)
    private List<HighRangeImage> imageFiles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "high_range_user_likes",
            joinColumns = @JoinColumn(name = "high_range_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"high_range_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    @Override
    public long getLikes() {
        return this.userLikes.size();
    }
}