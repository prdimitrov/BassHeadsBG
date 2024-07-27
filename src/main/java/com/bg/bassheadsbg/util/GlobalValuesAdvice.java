package com.bg.bassheadsbg.util;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
public class GlobalValuesAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(float.class, new CustomFloatEditor());
        binder.registerCustomEditor(byte.class, new CustomByteEditor());
        binder.registerCustomEditor(short.class, new CustomShortEditor());
        binder.registerCustomEditor(int.class, new CustomIntegerEditor());
        binder.registerCustomEditor(double.class, new CustomDoubleEditor());
    }

    private static class CustomFloatEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue(0.0f); // Default value when empty
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
                setValue((short) 0); // Default value when empty
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
                setValue(0); // Default value when empty
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
                setValue(0.0); // Default value when empty
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