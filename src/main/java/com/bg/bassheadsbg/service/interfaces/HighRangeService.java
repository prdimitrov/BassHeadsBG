package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface HighRangeService {
    AddHighRangeDTO createNewAddHighRangeDTO();

    long addDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException, DeviceAlreadyExistsException;

    long editDevice(AddHighRangeDTO addDeviceDTO) throws JsonProcessingException;

    void deleteDevice(long deviceId);

    HighRangeDetailsDTO getDeviceDetails(Long id);

    HighRangeDetailsHelperDTO getDeviceDetailsHelper(Long id);

    List<HighRangeSummaryDTO> getAllDeviceSummary();

    void likeDevice(Long id);

    void updateDeviceImageUrls(String oldUrl, String newUrl);
}