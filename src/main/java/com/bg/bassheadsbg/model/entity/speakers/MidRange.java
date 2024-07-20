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
@Table(name = "mid_range")
public class MidRange extends BaseSpeaker {

    @PositiveOrZero
    @NotNull
    private int frequencyRangeFrom;

    @PositiveOrZero
    @NotNull
    private int frequencyRangeTo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mid_range_user_likes",
            joinColumns = @JoinColumn(name = "mid_range_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"mid_range_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    public MidRange() {
        super();
    }

    public long getLikes() {
        return this.userLikes.size();
    }
}