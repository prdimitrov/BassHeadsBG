package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface SubwooferService {
    AddSubwooferDTO createNewSubwooferDTO();

    long addDevice(AddSubwooferDTO addDeviceDTO) throws JsonProcessingException, DeviceAlreadyExistsException;

    long editDevice(AddSubwooferDTO addDeviceDTO) throws JsonProcessingException;

    void deleteDevice(long deviceId);

    SubwooferDetailsDTO getDeviceDetails(Long id);

    SubwooferDetailsHelperDTO getDeviceDetailsHelper(Long id);

    List<SubwooferSummaryDTO> getAllDeviceSummary();

    void likeDevice(Long id);

    void updateDeviceImageUrls(String oldUrl, String newUrl);
}
