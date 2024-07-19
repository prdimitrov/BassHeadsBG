package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "multi_channel_amplifiers")
public class MultiChannelAmplifier extends BaseAmplifier {
    @NotNull
    @Positive
    private byte numberOfChannels;
    public MultiChannelAmplifier() {
        super();
    }

}
