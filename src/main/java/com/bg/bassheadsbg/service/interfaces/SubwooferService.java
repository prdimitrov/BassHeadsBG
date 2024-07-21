package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;

public interface SubwooferService extends CommonDeviceService<AddSubwooferDTO, SubwooferDetailsDTO, SubwooferSummaryDTO>  {
    void likeDevice(Long id);
}
