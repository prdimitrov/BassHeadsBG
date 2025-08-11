package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;

public interface MidRangeService extends DeviceService<
        AddMidRangeDTO, MidRangeDetailsDTO, MidRangeSummaryDTO, MidRangeDetailsHelperDTO> {
}