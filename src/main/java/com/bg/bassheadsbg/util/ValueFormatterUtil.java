package com.bg.bassheadsbg.util;

/**
 * Utility class, that is used for formatting values into user-friendly strings.
 * Handles null values, numbers, and other objects, providing more appropriate String representations.
 */
public class ValueFormatterUtil {

    /**
     * This method is used to format a given value into a string representation.
     * - Returns "---" for null values.
     * - Returns "---" for numbers that are zero.
     * - Formats integers, without decimal points.
     * - Formats other numbers with their decimal points.
     * - Trims and returns non-null strings, or "---" if the trimmed string is empty.
     *
     * @param value the value to format
     * @return the formatted String representation of the given value
     */
    public static String formatValue(Object value) {
        if (value == null) {
            return "---";
        } else if (value instanceof Number) {
            Number number = (Number) value;
            if (number.doubleValue() == 0) {
                return "---";
            } else if (number.doubleValue() % 1 == 0) {
                return String.valueOf(number.intValue());
            } else {
                return String.valueOf(number);
            }
        } else {
            String stringValue = value.toString().trim();
            return stringValue.isEmpty() ? "---" : stringValue;
        }
    }
}