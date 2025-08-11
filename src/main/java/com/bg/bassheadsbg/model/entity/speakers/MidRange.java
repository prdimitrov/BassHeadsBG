package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import com.bg.bassheadsbg.model.entity.images.MidRangeImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.interfaces.DeviceEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mid_range")
public class MidRange extends BaseSpeaker implements DeviceEntity<MidRangeImage> {

    @PositiveOrZero(message = "{frequencyRangeFrom.positiveOrZero}")
    @NotNull(message = "{frequencyRangeFrom.positiveOrZero}")
    @Max(value = 40000, message = "{frequencyRangeFrom.max40000}")
    private int frequencyRangeFrom;

    @PositiveOrZero(message = "{frequencyRangeTo.positiveOrZero}")
    @NotNull(message = "{frequencyRangeTo.positiveOrZero}")
    @Max(value = 80000, message = "{frequencyRangeTo.max80000}")
    private int frequencyRangeTo;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MidRangeImage> imageFiles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mid_range_user_likes",
            joinColumns = @JoinColumn(name = "mid_range_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"mid_range_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    @Override
    public long getLikes() {
        return this.userLikes.size();
    }
}