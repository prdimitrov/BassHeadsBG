package com.bg.bassheadsbg.util;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

/**
 * Class, that is used for custom property editors.
 * This class provides custom conversion logic for certain data types when binding request parameters to Java objects.
 */
@ControllerAdvice
public class GlobalValuesAdvice {

    /**
     * This method is used for registering the custom editors for different primitive data types.
     * These editors handle the conversion of string values to the specified primitive type.
     *
     * @param binder the WebDataBinder used to register custom editors
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(float.class, new CustomFloatEditor());
        binder.registerCustomEditor(byte.class, new CustomByteEditor());
        binder.registerCustomEditor(short.class, new CustomShortEditor());
        binder.registerCustomEditor(int.class, new CustomIntegerEditor());
        binder.registerCustomEditor(double.class, new CustomDoubleEditor());
    }

    /**
     * Custom editor method, used for parsing float values from strings.
     */
    private static class CustomFloatEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue(0.0f); //This is the default value, when it's empty!
            } else {
                try {
                    setValue(Float.parseFloat(text));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid float value: " + text);
                }
            }
        }
    }

    private static class CustomByteEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue((byte) 0); // Default value when empty
            } else {
                try {
                    setValue(Byte.parseByte(text));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid byte value: " + text);
                }
            }
        }
    }

    private static class CustomShortEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue((short) 0);
            } else {
                try {
                    setValue(Short.parseShort(text));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid short value: " + text);
                }
            }
        }
    }

    private static class CustomIntegerEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue(0);
            } else {
                try {
                    setValue(Integer.parseInt(text));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid integer value: " + text);
                }
            }
        }
    }

    private static class CustomDoubleEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue(0.0);
            } else {
                try {
                    setValue(Double.parseDouble(text));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid double value: " + text);
                }
            }
        }
    }
}