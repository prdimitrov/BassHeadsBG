package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.entity.DeviceEntity;
import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import com.bg.bassheadsbg.model.entity.images.MonoAmplifierImage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
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
@Table(name = "mono_amplifiers")
public class MonoAmplifier extends BaseAmplifier implements DeviceEntity<MonoAmplifierImage> {
    @NotNull(message = "{numberOfRca.positive}")
    @Positive(message = "{numberOfRca.positive}")
    @Max(value = 4, message = "{numberOfRca.max4}")
    private byte numberOfRca;

    @NotNull(message = "{numberOfSpeakerOutputs.positive}")
    @Positive(message = "{numberOfSpeakerOutputs.positive}")
    @Max(value = 16, message = "{numberOfSpeakerOutputs.max16}")
    private byte numberOfSpeakerOutputs;

    @OneToMany(mappedBy = "device", orphanRemoval = true)
    private List<MonoAmplifierImage> imageFiles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mono_amplifiers_user_likes",
            joinColumns = @JoinColumn(name = "mono_amplifier_id"),
            inverseJoinColumns = @JoinColumn(name = "user_likes_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"mono_amplifier_id", "user_likes_id"})
    )
    private List<UserEntity> userLikes = new ArrayList<>();

    @Override
    public long getLikes() {
        return this.userLikes.size();
    }
}