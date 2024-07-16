package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;

public final class MidRangeDetailsHelperDTO {
    private final MidRangeDetailsDTO midRangeDetails;

    public MidRangeDetailsHelperDTO(MidRangeDetailsDTO midRangeDetails) {
        this.midRangeDetails = midRangeDetails;
    }

    public String formattedSensitivity() {
        Float sensitivity = midRangeDetails.getSensitivity();
        if (sensitivity == null || ValueFormatterUtil.formatValue(sensitivity).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(sensitivity) + " dB (1W / 1m)";
    }

    public String formattedSize() {
        return ValueFormatterUtil.formatValue(midRangeDetails.getSize()) + "\"";
    }

    public String formattedFrequencyResponse() {
        return ValueFormatterUtil.formatValue(midRangeDetails.getFrequencyResponse()) + " Hz";
    }

    public String formattedNumberOfCoils() {
        return Byte.toString(midRangeDetails.getNumberOfCoils());
    }

    public String formattedImpedance() {
        return midRangeDetails.getImpedance() + " Ω";
    }

    public String formattedPowerHandling() {
        return midRangeDetails.getPowerHandling() + " W";
    }

    public String formattedFrequencyRange() {
        return midRangeDetails.getFrequencyRangeFrom() + " Hz - " + midRangeDetails.getFrequencyRangeTo() + " Hz";
    }
}