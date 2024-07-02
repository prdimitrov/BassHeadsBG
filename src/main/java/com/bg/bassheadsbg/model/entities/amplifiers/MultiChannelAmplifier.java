package com.bg.bassheadsbg.model.entities.amplifiers;

import com.bg.bassheadsbg.model.entities.base.BaseAmplifier;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "multi_channel_amplifiers")
public class MultiChannelAmplifier extends BaseAmplifier {

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
