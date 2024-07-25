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
public class AddMidRangeDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AddMidRangeDTO createValidDTO() {
        AddMidRangeDTO dto = new AddMidRangeDTO();
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
        dto.setFrequencyRangeFrom("0");
        dto.setFrequencyRangeTo("80000");
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        dto.setImages(images);
        return dto;
    }

    @Test
    public void testValidDTO() {
        AddMidRangeDTO dto = createValidDTO();
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setPrice("-1");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setBrand("");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setModel("mo");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSensitivity() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setSensitivity("151");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{sensitivity.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSize() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setSize("51");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{size.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyResponse() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setFrequencyResponse("20001");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyResponse.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfCoils() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setNumberOfCoils("5");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{numberOfCoils.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setImpedance("17");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerHandling() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setPowerHandling("32001");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{powerHandling.max32000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeFrom() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setFrequencyRangeFrom("40001");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeFrom.max40000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyRangeTo() {
        AddMidRangeDTO dto = createValidDTO();
        dto.setFrequencyRangeTo("80001");
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyRangeTo.max80000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        AddMidRangeDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        dto.setImages(images);
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        AddMidRangeDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("");
        dto.setImages(images);
        Set<ConstraintViolation<AddMidRangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}
