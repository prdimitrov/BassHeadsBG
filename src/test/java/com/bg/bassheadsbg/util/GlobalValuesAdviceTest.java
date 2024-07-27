package com.bg.bassheadsbg.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyEditorSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GlobalValuesAdviceTest {

    private GlobalValuesAdvice globalValuesAdvice;

    @BeforeEach
    public void setUp() {
        globalValuesAdvice = new GlobalValuesAdvice();
    }

    @Test
    public void testCustomFloatEditor() {
        CustomFloatEditor editor = new CustomFloatEditor();

        editor.setAsText("1.23");
        assertEquals(1.23f, editor.getValue());

        editor.setAsText("0.0");
        assertEquals(0.0f, editor.getValue());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> editor.setAsText("invalid"));
        assertEquals("Invalid float value: invalid", thrown.getMessage());

        editor.setAsText("");
        assertEquals(0.0f, editor.getValue());
    }

    @Test
    public void testCustomByteEditor() {
        CustomByteEditor editor = new CustomByteEditor();

        editor.setAsText("127");
        assertEquals((byte) 127, editor.getValue());

        editor.setAsText("0");
        assertEquals((byte) 0, editor.getValue());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> editor.setAsText("invalid"));
        assertEquals("Invalid byte value: invalid", thrown.getMessage());

        editor.setAsText("");
        assertEquals((byte) 0, editor.getValue());
    }

    @Test
    public void testCustomShortEditor() {
        CustomShortEditor editor = new CustomShortEditor();

        editor.setAsText("32767");
        assertEquals((short) 32767, editor.getValue());

        editor.setAsText("0");
        assertEquals((short) 0, editor.getValue());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> editor.setAsText("invalid"));
        assertEquals("Invalid short value: invalid", thrown.getMessage());

        editor.setAsText("");
        assertEquals((short) 0, editor.getValue());
    }

    @Test
    public void testCustomIntegerEditor() {
        CustomIntegerEditor editor = new CustomIntegerEditor();

        editor.setAsText("2147483647");
        assertEquals(2147483647, editor.getValue());

        editor.setAsText("0");
        assertEquals(0, editor.getValue());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> editor.setAsText("invalid"));
        assertEquals("Invalid integer value: invalid", thrown.getMessage());

        editor.setAsText("");
        assertEquals(0, editor.getValue());
    }

    @Test
    public void testCustomDoubleEditor() {
        CustomDoubleEditor editor = new CustomDoubleEditor();

        editor.setAsText("1.7976931348623157E308");
        assertEquals(1.7976931348623157E308, editor.getValue());

        editor.setAsText("0.0");
        assertEquals(0.0, editor.getValue());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> editor.setAsText("invalid"));
        assertEquals("Invalid double value: invalid", thrown.getMessage());

        editor.setAsText("");
        assertEquals(0.0, editor.getValue());
    }

    private static class CustomFloatEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue(0.0f);
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
                setValue((byte) 0);
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
