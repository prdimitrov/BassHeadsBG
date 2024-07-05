package com.bg.bassheadsbg.model.entity.amplifiers;

import com.bg.bassheadsbg.model.entity.base.BaseAmplifier;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "mono_amplifiers")
public class MonoAmplifier extends BaseAmplifier {
    @NotNull
    @Positive
    private byte numberOfRca;
    @NotNull
    @Positive
    private byte numberOfSpeakerOutputs;
    public MonoAmplifier() {
        super();
    }

    public byte getNumberOfRca() {
        return numberOfRca;
    }

    public void setNumberOfRca(byte numberOfRca) {
        this.numberOfRca = numberOfRca;
    }

    public byte getNumberOfSpeakerOutputs() {
        return numberOfSpeakerOutputs;
    }

    public void setNumberOfSpeakerOutputs(byte numberOfSpeakerOutputs) {
        this.numberOfSpeakerOutputs = numberOfSpeakerOutputs;
    }
}
