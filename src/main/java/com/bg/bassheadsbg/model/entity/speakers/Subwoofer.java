package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subwoofers")
public class Subwoofer extends BaseSpeaker {

    @Positive(message = "{coilHeight.positive}")
    @NotNull(message = "{coilHeight.positive}")
    @DecimalMax(value = "10", message = "{coilHeight.max10}")
    private float coilHeight;

    @PositiveOrZero(message = "{coilLayers.positiveOrZero}")
    @NotNull(message = "{coilLayers.positiveOrZero}")
    @Max(value = 20, message = "{coilLayers.max20}")
    private byte coilLayers;

    @Positive(message = "{magnetSize.positive}")
    @NotNull(message = "{magnetSize.positive}")
    @Max(value = 5000, message = "{magnetSize.max5000}")
    private short magnetSize;

    @PositiveOrZero(message = "{vas.positiveOrZero}")
    @NotNull(message = "{vas.positiveOrZero}")
    @DecimalMax(value = "1000", message = "{vas.max1000}")
    private float vas;

    @Positive(message = "{xmax.positive}")
    @NotNull(message = "{xmax.positive}")
    @Max(value = 100, message = "{xmax.max100}")
    private byte xmax;

    @PositiveOrZero(message = "{qms.positiveOrZero}")
    @NotNull(message = "{qms.positiveOrZero}")
    @DecimalMax(value = "30", message = "{qms.max30}")
    private float qms;

    @PositiveOrZero(message = "{qes.positiveOrZero}")
    @NotNull(message = "{qes.positiveOrZero}")
    @DecimalMax(value = "10", message = "{qes.max10}")
    private float qes;

    @PositiveOrZero(message = "{qts.positiveOrZero}")
    @NotNull(message = "{qts.positiveOrZero}")
    @DecimalMax(value = "10", message = "{qts.max10}")
    private float qts;

    @PositiveOrZero(message = "{sd.positiveOrZero}")
    @NotNull(message = "{sd.positiveOrZero}")
    @DecimalMax(value = "2000", message = "{sd.max2000}")
    private float sd;

    @PositiveOrZero(message = "{bl.positiveOrZero}")
    @NotNull(message = "{bl.positiveOrZero}")
    @DecimalMax(value = "150", message = "{bl.max150}")
    private float bl;

    @Positive(message = "{mms.positive}")
    @NotNull(message = "{mms.positive}")
    @DecimalMax(value = "3000", message = "{mms.max3000}")
    private float mms;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subwoofers_user_likes",
            joinColumns = @JoinColumn(name = "subwoofer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"subwoofer_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    public Subwoofer() {
        super();
    }

    public long getLikes() {
        return this.userLikes.size();
    }
}