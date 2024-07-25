package com.bg.bassheadsbg.model.dto.add;

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
public class AddHighRangeDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AddHighRangeDTO createValidDTO() {
        AddHighRangeDTO dto = new AddHighRangeDTO();
        dto.setId(1L);
        dto.setPrice("49999");
        dto.setBrand("BrandName");
        dto.setModel("ModelName");
        dto.setSensitivity("100");
        dto.setSize("45");
        dto.setFrequencyResponse("20000");
        dto.setNumberOfCoils("4");
        dto.setImpedance("16");
        dto.setPowerHandling("32000");
        dto.setMaterial("MaterialName");
        dto.setFrequencyRangeFrom("0");
        dto.setFrequencyRangeTo("80000");
        dto.setCrossover("CrossoverName");
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        dto.setImages(images);
        return dto;
    }

    @Test
    public void testValidDTO() {
        AddHighRangeDTO dto = createValidDTO();
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setPrice("-1");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setBrand("");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setModel("mo");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSensitivity() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setSensitivity("151");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{sensitivity.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSize() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setSize("51");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{size.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyResponse() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setFrequencyResponse("20001");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyResponse.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfCoils() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setNumberOfCoils("5");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{numberOfCoils.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setImpedance("17");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerHandling() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setPowerHandling("32001");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{powerHandling.max32000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidMaterial() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setMaterial("");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{material.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeFrom() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setFrequencyRangeFrom("40001");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeFrom.max40000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeTo() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setFrequencyRangeTo("80001");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeTo.max80000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCrossover() {
        AddHighRangeDTO dto = createValidDTO();
        dto.setCrossover("");
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{crossover.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        AddHighRangeDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        dto.setImages(images);
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        AddHighRangeDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("");
        dto.setImages(images);
        Set<ConstraintViolation<AddHighRangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}
