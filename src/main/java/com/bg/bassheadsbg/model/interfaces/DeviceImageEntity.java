package com.bg.bassheadsbg.model.interfaces;

public interface DeviceImageEntity<E extends DeviceEntity<?>> {
    void setImageData(byte[] bytes);

    void setDevice(E device);

    byte[] getImageData();
}