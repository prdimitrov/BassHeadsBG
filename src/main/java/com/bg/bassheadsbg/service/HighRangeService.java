package com.bg.bassheadsbg.service;


import com.bg.bassheadsbg.model.dto.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.HighRangeSummaryDTO;

import java.util.List;

public interface HighRangeService {
    long addHighRange(AddHighRangeDTO addHighRangeDTO);

    void deleteHighRange(long highRangeId);

    HighRangeDetailsDTO getHighRangeDetails(Long id);

    List<HighRangeSummaryDTO> getAllHighRangeSummary();
}
