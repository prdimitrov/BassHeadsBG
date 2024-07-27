package com.bg.bassheadsbg.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueFormatterUtilTest {

    @Test
    public void testFormatValueWithNull() {
        assertEquals("---", ValueFormatterUtil.formatValue(null));
    }

    @Test
    public void testFormatValueWithZeroNumber() {
        assertEquals("---", ValueFormatterUtil.formatValue(0));
        assertEquals("---", ValueFormatterUtil.formatValue(0.0));
    }

    @Test
    public void testFormatValueWithInteger() {
        assertEquals("1", ValueFormatterUtil.formatValue(1));
        assertEquals("123", ValueFormatterUtil.formatValue(123));
    }

    @Test
    public void testFormatValueWithDouble() {
        assertEquals("1.5", ValueFormatterUtil.formatValue(1.5));
        assertEquals("123.45", ValueFormatterUtil.formatValue(123.45));
    }

    @Test
    public void testFormatValueWithNegativeNumbers() {
        assertEquals("-1", ValueFormatterUtil.formatValue(-1));
        assertEquals("-123", ValueFormatterUtil.formatValue(-123));
        assertEquals("-1.5", ValueFormatterUtil.formatValue(-1.5));
        assertEquals("-123.45", ValueFormatterUtil.formatValue(-123.45));
    }

    @Test
    public void testFormatValueWithString() {
        assertEquals("Hello", ValueFormatterUtil.formatValue("Hello"));
        assertEquals("---", ValueFormatterUtil.formatValue("   "));
        assertEquals("World", ValueFormatterUtil.formatValue("   World   "));
    }

    @Test
    public void testFormatValueWithBoolean() {
        assertEquals("true", ValueFormatterUtil.formatValue(true));
        assertEquals("false", ValueFormatterUtil.formatValue(false));
    }
}
