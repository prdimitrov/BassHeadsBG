package com.bg.bassheadsbg.util;

public class ValueFormatterUtil {

    public static String formatValue(Object value) {
        if (value == null) {
            return "N/A";
        } else if (value instanceof Number) {
            Number number = (Number) value;
            if (number.doubleValue() == 0) {
                return "N/A";
            } else if (number.doubleValue() % 1 == 0) {
                return String.valueOf(number.intValue());
            } else {
                return String.valueOf(number);
            }
        } else {
            String stringValue = value.toString().trim();
            return stringValue.isEmpty() ? "N/A" : stringValue;
        }
    }
}