package com.bg.bassheadsbg.service;

import com.bg.bassheadsbg.model.dto.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.SubwooferSummaryDTO;

import java.util.List;

public interface SubwooferService {
    long addSubwoofer(AddSubwooferDTO addSubwooferDTO);

    void deleteSubwoofer(long subwooferId);

    SubwooferDetailsDTO getSubwooferDetails(Long id);

    List<SubwooferSummaryDTO> getAllSubwooferSummary();
}
