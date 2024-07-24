package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MultiChannelAmpService {
    AddMultiChannelAmpDTO createNewAddMultiChannelAmpDTO();

    long addDevice(AddMultiChannelAmpDTO addDeviceDTO) throws JsonProcessingException, DeviceAlreadyExistsException;

    long editDevice(AddMultiChannelAmpDTO addDeviceDTO) throws JsonProcessingException;

    void deleteDevice(long deviceId);

    MultiChannelAmpDetailsDTO getDeviceDetails(Long id);

    MultiChannelAmpDetailsHelperDTO getDeviceDetailsHelper(Long id);

    List<MultiChannelAmpSummaryDTO> getAllDeviceSummary();

    void likeDevice(Long id);

    void updateDeviceImageUrls(String oldUrl, String newUrl);
}