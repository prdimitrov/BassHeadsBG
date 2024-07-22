package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker {
    @NotBlank(message = "{material.notBlank}")
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
    @Column(nullable = false)
    private String crossover;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "high_range_user_likes",
            joinColumns = @JoinColumn(name = "high_range_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"high_range_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    public HighRange() {
        super();
    }

    public long getLikes() {
        return this.userLikes.size();
    }
}
