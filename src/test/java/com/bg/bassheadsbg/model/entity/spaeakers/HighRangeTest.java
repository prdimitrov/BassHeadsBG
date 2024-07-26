package com.bg.bassheadsbg.model.entity.spaeakers;

import com.bg.bassheadsbg.model.entity.speakers.HighRange;
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
public class HighRangeTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private HighRange createValidHighRange() {
        HighRange highRange = new HighRange();
        highRange.setId(1L);
        highRange.setPrice(49999);
        highRange.setBrand("BrandName");
        highRange.setModel("ModelName");
        highRange.setSensitivity(100);
        highRange.setSize(45);
        highRange.setFrequencyResponse(20000);
        highRange.setNumberOfCoils((byte) 4);
        highRange.setImpedance(16);
        highRange.setPowerHandling((short) 32000);
        highRange.setMaterial("MaterialName");
        highRange.setFrequencyRangeFrom(0);
        highRange.setFrequencyRangeTo(80000);
        highRange.setCrossover("CrossoverName");
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        highRange.setImages(images);
        return highRange;
    }

    @Test
    public void testValidHighRange() {
        HighRange highRange = createValidHighRange();
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        HighRange highRange = createValidHighRange();
        highRange.setPrice(-1);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        HighRange highRange = createValidHighRange();
        highRange.setBrand("");
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        HighRange highRange = createValidHighRange();
        highRange.setModel("mo");
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSensitivity() {
        HighRange highRange = createValidHighRange();
        highRange.setSensitivity(151);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{sensitivity.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSize() {
        HighRange highRange = createValidHighRange();
        highRange.setSize(51);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{size.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyResponse() {
        HighRange highRange = createValidHighRange();
        highRange.setFrequencyResponse(20001);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{frequencyResponse.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfCoils() {
        HighRange highRange = createValidHighRange();
        highRange.setNumberOfCoils((byte) 5);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{numberOfCoils.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        HighRange highRange = createValidHighRange();
        highRange.setImpedance(17);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerHandling() {
        HighRange highRange = createValidHighRange();
        highRange.setPowerHandling((short) 32001);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{powerHandling.max32000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidMaterial() {
        HighRange highRange = createValidHighRange();
        highRange.setMaterial("");
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{material.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeFrom() {
        HighRange highRange = createValidHighRange();
        highRange.setFrequencyRangeFrom(40001);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeFrom.max40000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeTo() {
        HighRange highRange = createValidHighRange();
        highRange.setFrequencyRangeTo(80001);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeTo.max80000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCrossover() {
        HighRange highRange = createValidHighRange();
        highRange.setCrossover("");
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(1, violations.size());
        assertEquals("{crossover.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        HighRange highRange = createValidHighRange();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        highRange.setImages(images);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        HighRange highRange = createValidHighRange();
        List<String> images = new ArrayList<>();
        images.add("");
        highRange.setImages(images);
        Set<ConstraintViolation<HighRange>> violations = validator.validate(highRange);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}
