package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero
    @NotNull
    private float coilHeight;

    @PositiveOrZero
    private byte coilLayers;

    @PositiveOrZero
    @NotNull
    private short magnetSize;

    @PositiveOrZero
    @NotNull
    private float vas;

    @PositiveOrZero
    @NotNull
    private byte xmax;

    @PositiveOrZero
    private float qms;

    @PositiveOrZero
    private float qes;

    @PositiveOrZero
    private float qts;

    @PositiveOrZero
    private float sd;

    @PositiveOrZero
    private float bl;

    @PositiveOrZero
    @NotNull
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