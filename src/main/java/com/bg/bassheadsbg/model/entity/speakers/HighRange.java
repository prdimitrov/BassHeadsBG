package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker {
    @NotBlank
    @Column(nullable = false)
    private String material;

    @PositiveOrZero
    @NotNull
    private int frequencyRangeFrom;

    @PositiveOrZero
    @NotNull
    private int frequencyRangeTo;

    @NotBlank
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
