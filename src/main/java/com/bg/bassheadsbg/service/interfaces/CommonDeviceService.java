package com.bg.bassheadsbg.service.interfaces;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface CommonDeviceService<AddDTO, DetailsDTO, SummaryDTO> {
    long editDevice(AddDTO addDeviceDTO);

    void deleteDevice(long deviceId);

    DetailsDTO getDeviceDetails(Long id);

    List<SummaryDTO> getAllDeviceSummary();
}