package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.PowerCableDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;

public final class PowerCableDetailsHelperDTO {
    private final PowerCableDetailsDTO powerCableDetailsDTO;

    public PowerCableDetailsHelperDTO(PowerCableDetailsDTO powerCableDetailsDTO) {
        this.powerCableDetailsDTO = powerCableDetailsDTO;
    }

    public String formattedLength() {
        Float length = powerCableDetailsDTO.getLength();
        if (length == null || ValueFormatterUtil.formatValue(length).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(length) + " m";
    }

    public String formattedThickness() {
        Float thickness = powerCableDetailsDTO.getThickness();
        if (thickness == null || ValueFormatterUtil.formatValue(thickness).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(thickness) + " mm2";
    }
}
