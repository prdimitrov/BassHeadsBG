package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.AmpClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AddMultiChannelAmpDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AddMultiChannelAmpDTO createValidDTO() {
        AddMultiChannelAmpDTO dto = new AddMultiChannelAmpDTO();
        dto.setId(1L);
        dto.setPrice("49999");
        dto.setBrand("BrandName");
        dto.setModel("ModelName");
        dto.setAmplifierClass(AmpClass.A);
        dto.setImpedance("16");
        dto.setPower("300000");
        dto.setHighPassFilter("HighPassFilter");
        dto.setLowPassFilter("LowPassFilter");
        dto.setSubsonicFilter("SubsonicFilter");
        dto.setRemoteControl(true);
        dto.setBassBoost(true);
        dto.setLowInputLevel("LowInputLevel");
        dto.setHighInputLevel("HighInputLevel");
        dto.setDistortion("25");
        dto.setCurrentDraw("15000");
        dto.setFuseRating("20000");
        dto.setNumberOfChannels("8");
        dto.setHeight("30000");
        dto.setWidth("30000");
        dto.setLength("30000");
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        dto.setImages(images);
        return dto;
    }

    @Test
    public void testValidDTO() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setPrice("-1");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setBrand("");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setModel("mo");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidAmplifierClass() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setAmplifierClass(null);
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{amplifierClass.notNull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setImpedance("17");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPower() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setPower("300001");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{power.max300000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighPassFilter() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setHighPassFilter("a".repeat(101));
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{highPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowPassFilter() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setLowPassFilter("a".repeat(101));
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{lowPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSubsonicFilter() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setSubsonicFilter("a".repeat(101));
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{subsonicFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputLevel() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setLowInputLevel("");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputLevel() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setHighInputLevel("");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDistortion() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setDistortion("51");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{distortion.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCurrentDraw() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setCurrentDraw("20001");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{currentDraw.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRating() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setFuseRating("20001");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfChannels() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setNumberOfChannels("17");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{numberOfChannels.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHeight() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setHeight("30001");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{height.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidWidth() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setWidth("30001");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{width.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLength() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        dto.setLength("30001");
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{length.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        dto.setImages(images);
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        AddMultiChannelAmpDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("");
        dto.setImages(images);
        Set<ConstraintViolation<AddMultiChannelAmpDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}