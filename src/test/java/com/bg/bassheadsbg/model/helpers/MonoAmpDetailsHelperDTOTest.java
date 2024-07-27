package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonoAmpDetailsHelperDTOTest {

    private MonoAmpDetailsDTO monoAmpDetailsDTO;
    private MonoAmpDetailsHelperDTO monoAmpDetailsHelperDTO;

    @BeforeEach
    public void setUp() {
        monoAmpDetailsDTO = new MonoAmpDetailsDTO();
        monoAmpDetailsDTO.setImpedance(4.0f);
        monoAmpDetailsDTO.setPower(500);
        monoAmpDetailsDTO.setHighPassFilter("30Hz");
        monoAmpDetailsDTO.setLowPassFilter("300Hz");
        monoAmpDetailsDTO.setSubsonicFilter("15Hz");
        monoAmpDetailsDTO.setRemoteControl(true);
        monoAmpDetailsDTO.setBassBoost(true);
        monoAmpDetailsDTO.setLowInputLevel("0.2V");
        monoAmpDetailsDTO.setHighInputLevel("4V");
        monoAmpDetailsDTO.setDistortion(0.05f);
        monoAmpDetailsDTO.setCurrentDraw(40.0);
        monoAmpDetailsDTO.setFuseRating((short) 30);
        monoAmpDetailsDTO.setHeight((short) 100);
        monoAmpDetailsDTO.setWidth((short) 250);
        monoAmpDetailsDTO.setLength((short) 300);
        monoAmpDetailsDTO.setNumberOfRca((byte) 2);
        monoAmpDetailsDTO.setNumberOfSpeakerOutputs((byte) 4);

        monoAmpDetailsHelperDTO = new MonoAmpDetailsHelperDTO(monoAmpDetailsDTO);
    }

    @Test
    public void testFormattedImpedance() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(4.0f)).thenReturn("4.0");

            String expected = "4.0 Ω";
            String result = monoAmpDetailsHelperDTO.formattedImpedance();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedPower() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(500)).thenReturn("500");

            String expected = "500 W";
            String result = monoAmpDetailsHelperDTO.formattedPower();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedHighPassFilter() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("30Hz")).thenReturn("30Hz");

            String expected = "30Hz";
            String result = monoAmpDetailsHelperDTO.formattedHighPassFilter();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedLowPassFilter() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("300Hz")).thenReturn("300Hz");

            String expected = "300Hz";
            String result = monoAmpDetailsHelperDTO.formattedLowPassFilter();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedSubsonicFilter() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("15Hz")).thenReturn("15Hz");

            String expected = "15Hz";
            String result = monoAmpDetailsHelperDTO.formattedSubsonicFilter();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testIsRemoteControl() {
        boolean expected = true;
        boolean result = monoAmpDetailsHelperDTO.isRemoteControl();

        assertEquals(expected, result);
    }

    @Test
    public void testIsBassBoost() {
        boolean expected = true;
        boolean result = monoAmpDetailsHelperDTO.isBassBoost();

        assertEquals(expected, result);
    }

    @Test
    public void testFormattedLowInputLevel() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("0.2V")).thenReturn("0.2V");

            String expected = "0.2V";
            String result = monoAmpDetailsHelperDTO.formattedLowInputLevel();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedHighInputLevel() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("4V")).thenReturn("4V");

            String expected = "4V";
            String result = monoAmpDetailsHelperDTO.formattedHighInputLevel();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedDistortion() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(0.05f)).thenReturn("0.05");

            String expected = ">0.05 %";
            String result = monoAmpDetailsHelperDTO.formattedDistortion();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedCurrentDraw() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(40.0)).thenReturn("40.0");

            String expected = "40.0 A";
            String result = monoAmpDetailsHelperDTO.formattedCurrentDraw();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedFuseRating() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) 30)).thenReturn("30");

            String expected = "30 A";
            String result = monoAmpDetailsHelperDTO.formattedFuseRating();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedHeight() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) 100)).thenReturn("100");

            String expected = "100 mm";
            String result = monoAmpDetailsHelperDTO.formattedHeight();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedWidth() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) 250)).thenReturn("250");

            String expected = "250 mm";
            String result = monoAmpDetailsHelperDTO.formattedWidth();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedLength() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) 300)).thenReturn("300");

            String expected = "300 mm";
            String result = monoAmpDetailsHelperDTO.formattedLength();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedNumberOfRca() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((byte) 2)).thenReturn("2");

            String expected = "2";
            String result = monoAmpDetailsHelperDTO.formattedNumberOfRca();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedNumberOfSpeakerOutputs() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((byte) 4)).thenReturn("4");

            String expected = "4";
            String result = monoAmpDetailsHelperDTO.formattedNumberOfSpeakerOutputs();

            assertEquals(expected, result);
        }
    }
}
