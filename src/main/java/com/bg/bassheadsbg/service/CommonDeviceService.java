package com.bg.bassheadsbg.service;

import java.util.List;

public interface CommonDeviceService<AddDTO, DetailsDTO, SummaryDTO> {
    long addDevice(AddDTO addDeviceDTO);

    void deleteDevice(long deviceId);

    DetailsDTO getDeviceDetails(Long id);

    List<SummaryDTO> getAllDeviceSummary();
}