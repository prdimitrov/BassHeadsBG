package com.bg.bassheadsbg.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CommonDeviceService<AddDTO, DetailsDTO, SummaryDTO> {
    long addDevice(AddDTO addDeviceDTO) throws JsonProcessingException;

    long editDevice(AddDTO addDeviceDTO);

    void deleteDevice(long deviceId);

    DetailsDTO getDeviceDetails(Long id);

    List<SummaryDTO> getAllDeviceSummary();
}