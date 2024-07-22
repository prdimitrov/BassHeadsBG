package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "multi_channel_amplifiers")
public class MultiChannelAmplifier extends BaseAmplifier {
    @Positive(message = "{numberOfChannels.positive}")
    @NotNull(message = "{numberOfChannels.positive}")
    @Max(value = 16, message = "{numberOfChannels.max16}")
    private byte numberOfChannels;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "multi_channel_amplifiers_user_likes",
            joinColumns = @JoinColumn(name = "multi_channel_amplifier_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"multi_channel_amplifier_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    public MultiChannelAmplifier() {
        super();
    }

    public long getLikes() {
        return this.userLikes.size();
    }

}
