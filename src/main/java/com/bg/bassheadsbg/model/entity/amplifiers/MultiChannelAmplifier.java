package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.interfaces.DeviceEntity;
import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import com.bg.bassheadsbg.model.entity.images.MultiChannelAmplifierImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
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
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "multi_channel_amplifiers")
public class MultiChannelAmplifier extends BaseAmplifier implements DeviceEntity<MultiChannelAmplifierImage> {
    @Positive(message = "{numberOfChannels.positive}")
    @NotNull(message = "{numberOfChannels.positive}")
    @Max(value = 16, message = "{numberOfChannels.max16}")
    private byte numberOfChannels;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultiChannelAmplifierImage> imageFiles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "multi_channel_amplifiers_user_likes",
            joinColumns = @JoinColumn(name = "multi_channel_amplifier_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"multi_channel_amplifier_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    @Override
    public long getLikes() {
        return this.userLikes.size();
    }
}