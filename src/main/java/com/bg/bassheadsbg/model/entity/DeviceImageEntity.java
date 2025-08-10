package com.bg.bassheadsbg.model.entity;

public interface DeviceImageEntity<E extends DeviceEntity<?>> {
    void setImageData(byte[] bytes);

    void setDevice(E device);

    byte[] getImageData();
}