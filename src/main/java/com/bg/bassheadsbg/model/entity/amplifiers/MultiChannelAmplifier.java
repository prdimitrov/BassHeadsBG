package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "multi_channel_amplifiers")
public class MultiChannelAmplifier extends BaseAmplifier {
    @NotNull
    @Positive
    private byte numberOfChannels;
    public MultiChannelAmplifier() {
        super();
    }

    public byte getNumberOfChannels() {
        return numberOfChannels;
    }

    public void setNumberOfChannels(byte numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }
}
