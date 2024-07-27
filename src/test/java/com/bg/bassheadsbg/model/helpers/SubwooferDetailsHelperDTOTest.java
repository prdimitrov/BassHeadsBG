package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubwooferDetailsHelperDTOTest {

    private SubwooferDetailsDTO subwooferDetailsDTO;
    private SubwooferDetailsHelperDTO subwooferDetailsHelperDTO;

    @BeforeEach
    public void setUp() {
        subwooferDetailsDTO = new SubwooferDetailsDTO();
        subwooferDetailsDTO.setSensitivity(90.0f);
        subwooferDetailsDTO.setSize(12.0f);
        subwooferDetailsDTO.setFrequencyResponse(4000);
        subwooferDetailsDTO.setNumberOfCoils((byte) 2);
        subwooferDetailsDTO.setImpedance(8.0f);
        subwooferDetailsDTO.setPowerHandling((short) 150);
        subwooferDetailsDTO.setCoilHeight(3.5f);
        subwooferDetailsDTO.setCoilLayers((byte) 4);
        subwooferDetailsDTO.setMagnetSize((short) 50);
        subwooferDetailsDTO.setVas(75.0f);
        subwooferDetailsDTO.setXmax((byte) 10);
        subwooferDetailsDTO.setQms(5.5f);
        subwooferDetailsDTO.setQes(0.4f);
        subwooferDetailsDTO.setQts(0.35f);
        subwooferDetailsDTO.setSd(800.0f);
        subwooferDetailsDTO.setBl(20.0f);
        subwooferDetailsDTO.setMms(50.0f);

        subwooferDetailsHelperDTO = new SubwooferDetailsHelperDTO(subwooferDetailsDTO);
    }

    @Test
    public void testFormattedSensitivity() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(90.0f)).thenReturn("90.0");

            String expected = "90.0dB (1W / 1m)";
            String result = subwooferDetailsHelperDTO.formattedSensitivity();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedSize() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(12.0f)).thenReturn("12.0");

            String expected = "12.0\"";
            String result = subwooferDetailsHelperDTO.formattedSize();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedFrequencyResponse() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(4000f)).thenReturn("4000");

            String expected = "4000Hz";
            String result = subwooferDetailsHelperDTO.formattedFrequencyResponse();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedNumberOfCoils() {
        String expected = "2";
        String result = subwooferDetailsHelperDTO.formattedNumberOfCoils();

        assertEquals(expected, result);
    }

    @Test
    public void testFormattedImpedance() {
        String expected = "8.0Ω";
        String result = subwooferDetailsHelperDTO.formattedImpedance();

        assertEquals(expected, result);
    }

    @Test
    public void testFormattedPowerHandling() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(150)).thenReturn("150");

            String expected = "150W";
            String result = subwooferDetailsHelperDTO.formattedPowerHandling();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedCoilHeight() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(3.5f)).thenReturn("3.5");

            String expected = "3.5\"";
            String result = subwooferDetailsHelperDTO.formattedCoilHeight();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedCoilLayers() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((byte) 4)).thenReturn("4");

            String expected = "4";
            String result = subwooferDetailsHelperDTO.formattedCoilLayers();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedMagnetSize() {
        String expected = "50oz";
        String result = subwooferDetailsHelperDTO.formattedMagnetSize();

        assertEquals(expected, result);
    }

    @Test
    public void testFormattedVas() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(75.0f)).thenReturn("75.0");

            String expected = "75.0L";
            String result = subwooferDetailsHelperDTO.formattedVas();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedXmax() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue((float) 10)).thenReturn("10.0");

            String expected = "10 mm";
            String result = subwooferDetailsHelperDTO.formattedXmax();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedQms() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(5.5f)).thenReturn("5.5");

            String expected = "5.5";
            String result = subwooferDetailsHelperDTO.formattedQms();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedQes() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(0.4f)).thenReturn("0.4");

            String expected = "0.4";
            String result = subwooferDetailsHelperDTO.formattedQes();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedQts() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(0.35f)).thenReturn("0.35");

            String expected = "0.35";
            String result = subwooferDetailsHelperDTO.formattedQts();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedSd() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(800.0f)).thenReturn("800.0");

            String expected = "800.0 mm²";
            String result = subwooferDetailsHelperDTO.formattedSd();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedBl() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(20.0f)).thenReturn("20.0");

            String expected = "20.0 T/m";
            String result = subwooferDetailsHelperDTO.formattedBl();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedMms() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(50.0f)).thenReturn("50.0");

            String expected = "50.0g";
            String result = subwooferDetailsHelperDTO.formattedMms();

            assertEquals(expected, result);
        }
    }
}
