package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MidRangeService {
    AddMidRangeDTO createNewAddMidRangeDTO();

    long addDevice(AddMidRangeDTO addDeviceDTO) throws JsonProcessingException, DeviceAlreadyExistsException;

    long editDevice(AddMidRangeDTO addDeviceDTO) throws JsonProcessingException;

    void deleteDevice(long deviceId);

    MidRangeDetailsDTO getDeviceDetails(Long id);

    MidRangeDetailsHelperDTO getDeviceDetailsHelper(Long id);

    List<MidRangeSummaryDTO> getAllDeviceSummary();

    void likeDevice(Long id);

    void updateDeviceImageUrls(String oldUrl, String newUrl);
}
