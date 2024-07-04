package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "subwoofers")
public class Subwoofer extends BaseSpeaker {
    @Positive
    private byte sensitivity;
    @Positive
    private float coilHeight;
    @Positive
    private Byte coilLayers;
    @Positive
    private short magnetSize;
    @Positive
    private Float vas;
    @Positive
    private byte xmax;
    @Positive
    private Float qms;
    @Positive
    private Float qes;
    @Positive
    private Float qts;
    @Positive
    private Float sd;
    @Positive
    private Float bl;
    @Positive
    private float mms;

    public Subwoofer() {
    }

    public byte getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(byte sensitivity) {
        this.sensitivity = sensitivity;
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
