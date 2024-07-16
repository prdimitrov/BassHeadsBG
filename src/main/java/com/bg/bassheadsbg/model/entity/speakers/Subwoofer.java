package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

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

    public Subwoofer() {
    }

    public float getCoilHeight() {
        return coilHeight;
    }

    public void setCoilHeight(float coilHeight) {
        this.coilHeight = coilHeight;
    }

    public byte getCoilLayers() {
        return coilLayers;
    }

    public void setCoilLayers(byte coilLayers) {
        this.coilLayers = coilLayers;
    }

    public short getMagnetSize() {
        return magnetSize;
    }

    public void setMagnetSize(short magnetSize) {
        this.magnetSize = magnetSize;
    }

    public float getVas() {
        return vas;
    }

    public void setVas(float vas) {
        this.vas = vas;
    }

    public byte getXmax() {
        return xmax;
    }

    public void setXmax(byte xmax) {
        this.xmax = xmax;
    }

    public float getQms() {
        return qms;
    }

    public void setQms(float qms) {
        this.qms = qms;
    }

    public float getQes() {
        return qes;
    }

    public void setQes(float qes) {
        this.qes = qes;
    }

    public float getQts() {
        return qts;
    }

    public void setQts(float qts) {
        this.qts = qts;
    }

    public float getSd() {
        return sd;
    }

    public void setSd(float sd) {
        this.sd = sd;
    }

    public float getBl() {
        return bl;
    }

    public void setBl(float bl) {
        this.bl = bl;
    }

    public float getMms() {
        return mms;
    }

    public void setMms(float mms) {
        this.mms = mms;
    }
}
