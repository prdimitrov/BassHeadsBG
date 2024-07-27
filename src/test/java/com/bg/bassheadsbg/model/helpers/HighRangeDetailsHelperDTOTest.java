package com.bg.bassheadsbg.model.helpers;

import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.util.ValueFormatterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighRangeDetailsHelperDTOTest {

    private HighRangeDetailsDTO highRangeDetailsDTO;
    private HighRangeDetailsHelperDTO highRangeDetailsHelperDTO;

    @BeforeEach
    public void setUp() {
        highRangeDetailsDTO = new HighRangeDetailsDTO();
        highRangeDetailsDTO.setSensitivity(89.5f);
        highRangeDetailsDTO.setSize(10.0f);
        highRangeDetailsDTO.setFrequencyResponse(19500);
        highRangeDetailsDTO.setImpedance(8.0f);
        highRangeDetailsDTO.setPowerHandling((short) 100);
        highRangeDetailsDTO.setFrequencyRangeFrom(10);
        highRangeDetailsDTO.setFrequencyRangeTo(10000);

        highRangeDetailsHelperDTO = new HighRangeDetailsHelperDTO(highRangeDetailsDTO);
    }

    @Test
    public void testFormattedSensitivity() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(89.5f)).thenReturn("89.5");

            String expected = "89.5 dB (1W / 1m)";
            String result = highRangeDetailsHelperDTO.formattedSensitivity();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedSize() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(10.0f)).thenReturn("10.0");

            String expected = "10.0\"";
            String result = highRangeDetailsHelperDTO.formattedSize();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedImpedance() {
        try (MockedStatic<ValueFormatterUtil> mockedUtil = Mockito.mockStatic(ValueFormatterUtil.class)) {
            mockedUtil.when(() -> ValueFormatterUtil.formatValue(8.0f)).thenReturn("8.0");

            String expected = "8.0 Ω";
            String result = highRangeDetailsHelperDTO.formattedImpedance();

            assertEquals(expected, result);
        }
    }

    @Test
    public void testFormattedFrequencyRange() {
        HighRangeDetailsDTO highRangeDetailsDTO = new HighRangeDetailsDTO();
        highRangeDetailsDTO.setFrequencyRangeFrom(10);
        highRangeDetailsDTO.setFrequencyRangeTo(10000);

        HighRangeDetailsHelperDTO helperDTO = new HighRangeDetailsHelperDTO(highRangeDetailsDTO);

        String formattedRange = helperDTO.formattedFrequencyRange();

        assertEquals("10 Hz - 10000 Hz", formattedRange);
    }
}
