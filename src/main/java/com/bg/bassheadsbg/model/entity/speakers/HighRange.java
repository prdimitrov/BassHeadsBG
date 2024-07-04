package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker {
    private String material;
    @NotBlank
    private String frequencyRange;
    public HighRange() {
        super();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public void setFrequencyRange(String frequencyRange) {
        this.frequencyRange = frequencyRange;
    }
}
