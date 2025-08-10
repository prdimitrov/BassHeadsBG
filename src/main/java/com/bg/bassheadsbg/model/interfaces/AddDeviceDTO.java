package com.bg.bassheadsbg.model.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AddDeviceDTO {
    long getId();

    String getBrand();

    String getModel();

    List<MultipartFile> getImageFiles();
}