package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.SubwooferDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;

public final class SubwooferDetailsHelperDTO {
    private final SubwooferDetailsDTO subwooferDetails;

    public SubwooferDetailsHelperDTO(SubwooferDetailsDTO subwooferDetails) {
        this.subwooferDetails = subwooferDetails;
    }

    public String formattedSensitivity() {
        Float sensitivity = subwooferDetails.getSensitivity();
        if (sensitivity == null || ValueFormatterUtil.formatValue(sensitivity).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(sensitivity) + "dB (1W / 1m)";
    }

    public String formattedSize() {
        return ValueFormatterUtil.formatValue(subwooferDetails.getSize()) + "\"";
    }

    public String formattedFrequencyResponse() {
        return ValueFormatterUtil.formatValue(subwooferDetails.getFrequencyResponse()) + "Hz";
    }

    public String formattedNumberOfCoils() {
        return Byte.toString(subwooferDetails.getNumberOfCoils());
    }

    public String formattedImpedance() {
        return subwooferDetails.getImpedance() + "Ω";
    }

    public String formattedPowerHandling() {
        return subwooferDetails.getPowerHandling() + "W";
    }

    public String formattedCoilHeight() {
        return ValueFormatterUtil.formatValue(subwooferDetails.getCoilHeight()) + "\"";
    }

    public String formattedCoilLayers() {
        Byte coilLayers = subwooferDetails.getCoilLayers();
        if (coilLayers == null || ValueFormatterUtil.formatValue(coilLayers).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(coilLayers);
    }

    public String formattedMagnetSize() {
        return subwooferDetails.getMagnetSize() + "oz";
    }

    public String formattedVas() {
        Float vas = subwooferDetails.getVas();
        if (vas == null || ValueFormatterUtil.formatValue(vas).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(vas) + "L";
    }

    public String formattedXmax() {
        return subwooferDetails.getXmax() + " mm";
    }

    public String formattedQms() {
        Float qms = subwooferDetails.getQms();
        if (qms == null || ValueFormatterUtil.formatValue(qms).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(qms);
    }

    public String formattedQes() {
        Float qes = subwooferDetails.getQes();
        if (qes == null || ValueFormatterUtil.formatValue(qes).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(qes);
    }

    public String formattedQts() {
        Float qts = subwooferDetails.getQts();
        if (qts == null || ValueFormatterUtil.formatValue(qts).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(qts);
    }

    public String formattedSd() {
        Float sd = subwooferDetails.getSd();
        if (sd == null || ValueFormatterUtil.formatValue(sd).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(sd) + " mm²";
    }

    public String formattedBl() {
        Float bl = subwooferDetails.getBl();
        if (bl == null || ValueFormatterUtil.formatValue(bl).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(bl) + " T/m";
    }

    public String formattedMms() {
        return ValueFormatterUtil.formatValue(subwooferDetails.getMms()) + "g";
    }
}