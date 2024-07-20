package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mono_amplifiers")
public class MonoAmplifier extends BaseAmplifier {
    @NotNull
    @Positive
    private byte numberOfRca;
    @NotNull
    @Positive
    private byte numberOfSpeakerOutputs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mono_amplifiers_user_likes",
            joinColumns = @JoinColumn(name = "mono_amplifier_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"mono_amplifier_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    public MonoAmplifier() {
        super();
    }

    public long getLikes() {
        return this.userLikes.size();
    }
}
