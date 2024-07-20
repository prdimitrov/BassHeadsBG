package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;

public interface MonoAmpService extends CommonDeviceService<AddMonoAmpDTO, MonoAmpDetailsDTO, MonoAmpSummaryDTO> {

    void likeDevice(Long id);
}
