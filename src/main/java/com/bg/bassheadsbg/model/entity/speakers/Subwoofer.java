package com.bg.bassheadsbg.model.entity.speakers;

import com.bg.bassheadsbg.model.entity.base.BaseSpeaker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "subwoofers")
public class Subwoofer extends BaseSpeaker {
    @Column(nullable = false)
    private byte sensitivity;
    private float coilHeight;
    private byte coilLayers;
    private short magnetSize;
    private float vas;
    private byte xmax;
    private float qms;
    private float qes;
    private float qts;
    private float sd;
    private float bl;
    private float z;
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getMms() {
        return mms;
    }

    public void setMms(float mms) {
        this.mms = mms;
    }
}
