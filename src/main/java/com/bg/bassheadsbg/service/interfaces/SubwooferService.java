package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;

public interface SubwooferService extends DeviceService<
        AddSubwooferDTO, SubwooferDetailsDTO, SubwooferSummaryDTO, SubwooferDetailsHelperDTO> {
}
