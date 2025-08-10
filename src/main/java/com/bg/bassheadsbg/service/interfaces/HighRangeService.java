package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;

public interface HighRangeService extends DeviceService<
        AddHighRangeDTO, HighRangeDetailsDTO, HighRangeSummaryDTO, HighRangeDetailsHelperDTO> {
}