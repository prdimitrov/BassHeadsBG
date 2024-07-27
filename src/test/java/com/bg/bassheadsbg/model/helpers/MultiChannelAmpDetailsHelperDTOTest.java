package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiChannelAmpDetailsHelperDTOTest {

    private MultiChannelAmpDetailsDTO multiChannelAmpDetailsDTO;
    private MultiChannelAmpDetailsHelperDTO multiChannelAmpDetailsHelperDTO;

    @BeforeEach
    public void setUp() {
        multiChannelAmpDetailsDTO = new MultiChannelAmpDetailsDTO();
        multiChannelAmpDetailsDTO.setImpedance(8.0f);
        multiChannelAmpDetailsDTO.setPower(600);
        multiChannelAmpDetailsDTO.setHighPassFilter("20Hz");
        multiChannelAmpDetailsDTO.setLowPassFilter("500Hz");
        multiChannelAmpDetailsDTO.setSubsonicFilter("10Hz");
        multiChannelAmpDetailsDTO.setRemoteControl(true);
        multiChannelAmpDetailsDTO.setBassBoost(true);
        multiChannelAmpDetailsDTO.setLowInputLevel("0.5V");
        multiChannelAmpDetailsDTO.setHighInputLevel("5V");
        multiChannelAmpDetailsDTO.setDistortion(0.1f);
        multiChannelAmpDetailsDTO.setCurrentDraw(50.0);
        multiChannelAmpDetailsDTO.setFuseRating((short) 40);
        multiChannelAmpDetailsDTO.setHeight((short) 120);
        multiChannelAmpDetailsDTO.setWidth((short) 300);
        multiChannelAmpDetailsDTO.setLength((short) 350);
        multiChannelAmpDetailsDTO.setNumberOfChannels((byte) 5);

        multiChannelAmpDetailsHelperDTO = new MultiChannelAmpDetailsHelperDTO(multiChannelAmpDetailsDTO);
    }

    @Test
    public void testFormattedImpedance() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(8.0f)).thenReturn("8.0");

            String expected = "8.0 Ω";
            String result = multiChannelAmpDetailsHelperDTO.formattedImpedance();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedPower() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(600)).thenReturn("600");

            String expected = "600 W";
            String result = multiChannelAmpDetailsHelperDTO.formattedPower();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedHighPassFilter() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("20Hz")).thenReturn("20Hz");

            String expected = "20Hz";
            String result = multiChannelAmpDetailsHelperDTO.formattedHighPassFilter();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedLowPassFilter() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("500Hz")).thenReturn("500Hz");

            String expected = "500Hz";
            String result = multiChannelAmpDetailsHelperDTO.formattedLowPassFilter();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedSubsonicFilter() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("10Hz")).thenReturn("10Hz");

            String expected = "10Hz";
            String result = multiChannelAmpDetailsHelperDTO.formattedSubsonicFilter();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testIsRemoteControl() {
        boolean expected = true;
        boolean result = multiChannelAmpDetailsHelperDTO.isRemoteControl();

        assertEquals(expected, result);
    }

    @Test
    public void testIsBassBoost() {
        boolean expected = true;
        boolean result = multiChannelAmpDetailsHelperDTO.isBassBoost();

        assertEquals(expected, result);
    }

    @Test
    public void testFormattedLowInputLevel() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("0.5V")).thenReturn("0.5V");

            String expected = "0.5V";
            String result = multiChannelAmpDetailsHelperDTO.formattedLowInputLevel();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedHighInputLevel() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue("5V")).thenReturn("5V");

            String expected = "5V";
            String result = multiChannelAmpDetailsHelperDTO.formattedHighInputLevel();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedDistortion() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(0.1f)).thenReturn("0.1");

            String expected = ">0.1 %";
            String result = multiChannelAmpDetailsHelperDTO.formattedDistortion();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedCurrentDraw() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(50.0)).thenReturn("50.0");

            String expected = "50.0 A";
            String result = multiChannelAmpDetailsHelperDTO.formattedCurrentDraw();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedFuseRating() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) 40)).thenReturn("40");

            String expected = "40 A";
            String result = multiChannelAmpDetailsHelperDTO.formattedFuseRating();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedNumberOfChannels() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((byte) 5)).thenReturn("5");

            String expected = "5";
            String result = multiChannelAmpDetailsHelperDTO.formattedNumberOfChannels();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedHeightWhenDefaultValue() {
        multiChannelAmpDetailsDTO.setHeight((short) -1);
        multiChannelAmpDetailsHelperDTO = new MultiChannelAmpDetailsHelperDTO(multiChannelAmpDetailsDTO);

        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) -1)).thenReturn("---");

            String expected = "---";
            String result = multiChannelAmpDetailsHelperDTO.formattedHeight();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedWidthWhenDefaultValue() {
        multiChannelAmpDetailsDTO.setWidth((short) -1);
        multiChannelAmpDetailsHelperDTO = new MultiChannelAmpDetailsHelperDTO(multiChannelAmpDetailsDTO);

        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) -1)).thenReturn("---");

            String expected = "---";
            String result = multiChannelAmpDetailsHelperDTO.formattedWidth();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedLengthWhenDefaultValue() {
        multiChannelAmpDetailsDTO.setLength((short) -1);
        multiChannelAmpDetailsHelperDTO = new MultiChannelAmpDetailsHelperDTO(multiChannelAmpDetailsDTO);

        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((short) -1)).thenReturn("---");

            String expected = "---";
            String result = multiChannelAmpDetailsHelperDTO.formattedLength();

            assertEquals(expected, result);
        }
    }
}