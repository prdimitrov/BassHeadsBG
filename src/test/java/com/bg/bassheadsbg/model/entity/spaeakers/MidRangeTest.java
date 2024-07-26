package com.bg.bassheadsbg.model.entity.spaeakers;

import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MidRangeTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private MidRange createValidMidRange() {
        MidRange midRange = new MidRange();
        midRange.setId(1L);
        midRange.setPrice(49999);
        midRange.setBrand("BrandName");
        midRange.setModel("ModelName");
        midRange.setSensitivity(100);
        midRange.setSize(45);
        midRange.setFrequencyResponse(20000);
        midRange.setNumberOfCoils((byte) 4);
        midRange.setImpedance(16);
        midRange.setPowerHandling((short) 32000);
        midRange.setFrequencyRangeFrom(0);
        midRange.setFrequencyRangeTo(80000);
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        midRange.setImages(images);
        return midRange;
    }

    @Test
    public void testValidMidRange() {
        MidRange midRange = createValidMidRange();
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        MidRange midRange = createValidMidRange();
        midRange.setPrice(-1);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        MidRange midRange = createValidMidRange();
        midRange.setBrand("");
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        MidRange midRange = createValidMidRange();
        midRange.setModel("mo");
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSensitivity() {
        MidRange midRange = createValidMidRange();
        midRange.setSensitivity(151);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{sensitivity.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSize() {
        MidRange midRange = createValidMidRange();
        midRange.setSize(51);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{size.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyResponse() {
        MidRange midRange = createValidMidRange();
        midRange.setFrequencyResponse(20001);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{frequencyResponse.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfCoils() {
        MidRange midRange = createValidMidRange();
        midRange.setNumberOfCoils((byte) 5);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{numberOfCoils.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        MidRange midRange = createValidMidRange();
        midRange.setImpedance(17);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerHandling() {
        MidRange midRange = createValidMidRange();
        midRange.setPowerHandling((short) 32001);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{powerHandling.max32000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeFrom() {
        MidRange midRange = createValidMidRange();
        midRange.setFrequencyRangeFrom(40001);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeFrom.max40000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeTo() {
        MidRange midRange = createValidMidRange();
        midRange.setFrequencyRangeTo(80001);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeTo.max80000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        MidRange midRange = createValidMidRange();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        midRange.setImages(images);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        MidRange midRange = createValidMidRange();
        List<String> images = new ArrayList<>();
        images.add("");
        midRange.setImages(images);
        Set<ConstraintViolation<MidRange>> violations = validator.validate(midRange);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}
