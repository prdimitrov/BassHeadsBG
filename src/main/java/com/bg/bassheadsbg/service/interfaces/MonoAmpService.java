package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;

public interface MonoAmpService extends DeviceService<
        AddMonoAmpDTO, MonoAmpDetailsDTO, MonoAmpSummaryDTO, MonoAmpDetailsHelperDTO> {
}