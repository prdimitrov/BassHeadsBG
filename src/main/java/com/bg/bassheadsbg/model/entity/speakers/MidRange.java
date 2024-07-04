package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "mid_range")
public class MidRange extends BaseSpeaker {

    @NotBlank
    private String frequencyRange;

    public MidRange() {
        super();
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public void setFrequencyRange(String frequencyRange) {
        this.frequencyRange = frequencyRange;
    }
}
