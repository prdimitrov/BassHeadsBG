package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MonoAmpService {
    AddMonoAmpDTO createNewAddMonoAmpDTO();

    long addDevice(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException, DeviceAlreadyExistsException;

    long editDevice(AddMonoAmpDTO addDeviceDTO) throws JsonProcessingException;

    void deleteDevice(long deviceId);

    MonoAmpDetailsDTO getDeviceDetails(Long id);

    MonoAmpDetailsHelperDTO getDeviceDetailsHelper(Long id);

    List<MonoAmpSummaryDTO> getAllDeviceSummary();

    void likeDevice(Long id);

    void updateDeviceImageUrls(String oldUrl, String newUrl);
}