package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;

public final class MonoAmpDetailsHelperDTO {
    private MonoAmpDetailsDTO monoAmpDetails;


    public MonoAmpDetailsHelperDTO(MonoAmpDetailsDTO monoAmpDetails) {
        this.monoAmpDetails = monoAmpDetails;
    }

    public MonoAmpDetailsHelperDTO() {
    }

    public String formattedImpedance() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getImpedance()) + " Ω";
    }

    public String formattedPower() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getPower()) + " W";
    }

    public String formattedHighPassFilter() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getHighPassFilter());
    }

    public String formattedLowPassFilter() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getLowPassFilter());
    }

    public String formattedSubsonicFilter() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getSubsonicFilter());
    }

    public boolean isRemoteControl() {
        return monoAmpDetails.isRemoteControl();
    }

    public boolean isBassBoost() {
        return monoAmpDetails.isBassBoost();
    }

    public String formattedLowInputLevel() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getLowInputLevel());
    }

    public String formattedHighInputLevel() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getHighInputLevel());
    }

    public String formattedDistortion() {
        return ">" + ValueFormatterUtil.formatValue(monoAmpDetails.getDistortion()) + " %";
    }

    public String formattedCurrentDraw() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getCurrentDraw()) + " A";
    }

    public String formattedFuseRating() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getFuseRating()) + " A";
    }

    public String formattedHeight() {
        Short height = monoAmpDetails.getHeight();
        if (height == null || ValueFormatterUtil.formatValue(height).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(height) + " mm";
    }

    public String formattedWidth() {
        Short width = monoAmpDetails.getWidth();
        if (width == null || ValueFormatterUtil.formatValue(width).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(width) + " mm";
    }

    public String formattedLength() {
        Short length = monoAmpDetails.getLength();
        if (length == null || ValueFormatterUtil.formatValue(length).equals("---")) {
            return "---";
        }
        return ValueFormatterUtil.formatValue(length) + " mm";
    }

    public String formattedNumberOfRca() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getNumberOfRca());
    }

    public String formattedNumberOfSpeakerOutputs() {
        return ValueFormatterUtil.formatValue(monoAmpDetails.getNumberOfSpeakerOutputs());
    }
}