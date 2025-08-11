package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddPowerCableDTO;
import com.bg.bassheadsbg.model.dto.details.PowerCableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.PowerCableSummaryDTO;
import com.bg.bassheadsbg.model.helpers.PowerCableDetailsHelperDTO;

public interface PowerCableService extends DeviceService<
        AddPowerCableDTO, PowerCableDetailsDTO, PowerCableSummaryDTO, PowerCableDetailsHelperDTO> {
}
