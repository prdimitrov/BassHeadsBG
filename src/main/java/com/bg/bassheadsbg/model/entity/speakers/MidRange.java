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
@Table(name = "mid_range")
public class MidRange extends BaseSpeaker {

    @PositiveOrZero(message = "{frequencyRangeFrom.positiveOrZero}")
    @NotNull(message = "{frequencyRangeFrom.positiveOrZero}")
    @Max(value = 40000, message = "{frequencyRangeFrom.max40000}")
    private int frequencyRangeFrom;

    @PositiveOrZero(message = "{frequencyRangeTo.positiveOrZero}")
    @NotNull(message = "{frequencyRangeTo.positiveOrZero}")
    @Max(value = 80000, message = "{frequencyRangeTo.max80000}")
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