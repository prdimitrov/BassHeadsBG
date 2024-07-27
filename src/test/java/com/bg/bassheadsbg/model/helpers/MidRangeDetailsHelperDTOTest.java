package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MidRangeDetailsHelperDTOTest {

    private MidRangeDetailsDTO midRangeDetailsDTO;
    private MidRangeDetailsHelperDTO midRangeDetailsHelperDTO;

    @BeforeEach
    public void setUp() {
        midRangeDetailsDTO = new MidRangeDetailsDTO();
        midRangeDetailsDTO.setSensitivity(85.0f);
        midRangeDetailsDTO.setSize(8.0f);
        midRangeDetailsDTO.setFrequencyResponse(5000);
        midRangeDetailsDTO.setNumberOfCoils((byte) 2);
        midRangeDetailsDTO.setImpedance(4.0f);
        midRangeDetailsDTO.setPowerHandling((short) 50);
        midRangeDetailsDTO.setFrequencyRangeFrom(100);
        midRangeDetailsDTO.setFrequencyRangeTo(5000);

        midRangeDetailsHelperDTO = new MidRangeDetailsHelperDTO(midRangeDetailsDTO);
    }

    @Test
    public void testFormattedSensitivity() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(85.0f)).thenReturn("85.0");

            String expected = "85.0 dB (1W / 1m)";
            String result = midRangeDetailsHelperDTO.formattedSensitivity();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedSize() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(8.0f)).thenReturn("8.0");

            String expected = "8.0\"";
            String result = midRangeDetailsHelperDTO.formattedSize();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedNumberOfCoils() {
        String expected = "2";
        String result = midRangeDetailsHelperDTO.formattedNumberOfCoils();

        assertEquals(expected, result);
    }

    @Test
    public void testFormattedImpedance() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(4.0f)).thenReturn("4.0");

            String expected = "4.0 Ω";
            String result = midRangeDetailsHelperDTO.formattedImpedance();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedPowerHandling() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(50)).thenReturn("50");

            String expected = "50 W";
            String result = midRangeDetailsHelperDTO.formattedPowerHandling();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedFrequencyRange() {
        String expected = "100 Hz - 5000 Hz";
        String result = midRangeDetailsHelperDTO.formattedFrequencyRange();

        assertEquals(expected, result);
    }
}
