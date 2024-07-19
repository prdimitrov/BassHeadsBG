package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subwoofers")
public class Subwoofer extends BaseSpeaker {

    @PositiveOrZero
    @NotNull
    private float coilHeight;

    @PositiveOrZero
    private byte coilLayers;

    @PositiveOrZero
    @NotNull
    private short magnetSize;

    @PositiveOrZero
    @NotNull
    private float vas;

    @PositiveOrZero
    @NotNull
    private byte xmax;

    @PositiveOrZero
    private float qms;

    @PositiveOrZero
    private float qes;

    @PositiveOrZero
    private float qts;

    @PositiveOrZero
    private float sd;

    @PositiveOrZero
    private float bl;

    @PositiveOrZero
    @NotNull
    private float mms;

}
