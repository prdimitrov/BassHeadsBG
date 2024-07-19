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

}
