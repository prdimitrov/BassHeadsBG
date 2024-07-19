package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;

public final class HighRangeDetailsHelperDTO {
    private final HighRangeDetailsDTO highRangeDetails;

    public HighRangeDetailsHelperDTO(HighRangeDetailsDTO highRangeDetails) {
        this.highRangeDetails = highRangeDetails;
    }

    public String formattedSensitivity() {
        if (ValueFormatterUtil.formatValue(highRangeDetails.getSensitivity()).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(highRangeDetails.getSensitivity()) + " dB (1W / 1m)";
    }

    public String formattedSize() {
        return ValueFormatterUtil.formatValue(highRangeDetails.getSize()) + "\"";
    }

    public String formattedFrequencyResponse() {
        if (ValueFormatterUtil.formatValue(highRangeDetails.getFrequencyResponse()).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(highRangeDetails.getFrequencyResponse()) + " Hz";
    }

    public String formattedImpedance() {
        return ValueFormatterUtil.formatValue(highRangeDetails.getImpedance()) + " Ω";
    }

    public String formattedPowerHandling() {
        return ValueFormatterUtil.formatValue(highRangeDetails.getPowerHandling()) + " W";
    }

    public String formattedFrequencyRange() {
        return ValueFormatterUtil.formatValue(highRangeDetails.getFrequencyRangeFrom()) +
                " Hz - " +
                ValueFormatterUtil.formatValue(highRangeDetails.getFrequencyRangeTo() +
                        " Hz");
    }

}