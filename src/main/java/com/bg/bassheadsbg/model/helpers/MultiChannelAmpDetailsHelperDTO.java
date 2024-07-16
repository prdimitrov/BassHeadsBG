package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;

public final class MultiChannelAmpDetailsHelperDTO {
    private final MultiChannelAmpDetailsDTO multiChannelAmpDetails;

    public MultiChannelAmpDetailsHelperDTO(MultiChannelAmpDetailsDTO multiChannelAmpDetails) {
        this.multiChannelAmpDetails = multiChannelAmpDetails;
    }

    public String formattedImpedance() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getImpedance()) + " Ω";
    }

    public String formattedPower() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getPower()) + " W";
    }

    public String formattedHighPassFilter() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getHighPassFilter());
    }

    public String formattedLowPassFilter() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getLowPassFilter());
    }

    public String formattedSubsonicFilter() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getSubsonicFilter());
    }

    public boolean isRemoteControl() {
        return multiChannelAmpDetails.isRemoteControl();
    }

    public boolean isBassBoost() {
        return multiChannelAmpDetails.isBassBoost();
    }

    public String formattedLowInputLevel() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getLowInputLevel());
    }

    public String formattedHighInputLevel() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getHighInputLevel());
    }

    public String formattedDistortion() {
        return ">" + ValueFormatterUtil.formatValue(multiChannelAmpDetails.getDistortion()) + " %";
    }

    public String formattedCurrentDraw() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getCurrentDraw()) + " A";
    }

    public String formattedFuseRating() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getFuseRating()) + " A";
    }

    public String formattedNumberOfChannels() {
        return ValueFormatterUtil.formatValue(multiChannelAmpDetails.getNumberOfChannels());
    }

    public String formattedHeight() {
        Short height = multiChannelAmpDetails.getHeight();
        if (height == null || ValueFormatterUtil.formatValue(height).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(height) + " mm";
    }

    public String formattedWidth() {
        Short width = multiChannelAmpDetails.getWidth();
        if (width == null || ValueFormatterUtil.formatValue(width).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(width) + " mm";
    }

    public String formattedLength() {
        Short length = multiChannelAmpDetails.getLength();
        if (length == null || ValueFormatterUtil.formatValue(length).equals("N/A")) {
            return "N/A";
        }
        return ValueFormatterUtil.formatValue(length) + " mm";
    }
}