package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "high_range")
public class HighRange extends BaseSpeaker {
    @NotBlank
    @Column(nullable = false)
    private String material;

    @PositiveOrZero
    @NotNull
    private int frequencyRangeFrom;

    @PositiveOrZero
    @NotNull
    private int frequencyRangeTo;

    @NotBlank
    @Column(nullable = false)
    private String crossover;

    public HighRange() {
        super();
    }

}
