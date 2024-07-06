package com.bg.bassheadsbg.service;


import com.bg.bassheadsbg.model.dto.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.MidRangeSummaryDTO;

import java.util.List;

public interface MidRangeService {
    long addMidRange(AddMidRangeDTO addMidRangeDTO);

    void deleteMidRange(long midRangeId);

    MidRangeDetailsDTO getMidRangeDetails(Long id);

    List<MidRangeSummaryDTO> getAllMidRangeSummary();
}
