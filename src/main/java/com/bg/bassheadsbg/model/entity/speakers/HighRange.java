package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker {
    @NotBlank
    private String material;

    @Positive
    @NotNull
    private int frequencyRangeFrom;

    @Positive
    @NotNull
    private int frequencyRangeTo;

    @NotBlank
    private String crossover;

    public HighRange() {
        super();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getFrequencyRangeFrom() {
        return frequencyRangeFrom;
    }

    public void setFrequencyRangeFrom(int frequencyRangeFrom) {
        this.frequencyRangeFrom = frequencyRangeFrom;
    }

    public int getFrequencyRangeTo() {
        return frequencyRangeTo;
    }

    public void setFrequencyRangeTo(int frequencyRangeTo) {
        this.frequencyRangeTo = frequencyRangeTo;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }
}
