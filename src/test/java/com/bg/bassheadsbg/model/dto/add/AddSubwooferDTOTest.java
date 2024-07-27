package com.bg.bassheadsbg.model.dto.add;

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
public class AddSubwooferDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AddSubwooferDTO createValidDTO() {
        AddSubwooferDTO dto = new AddSubwooferDTO();
        dto.setId(1L);
        dto.setPrice("49999");
        dto.setBrand("BrandName");
        dto.setModel("ModelName");
        dto.setSensitivity("150");
        dto.setSize("50");
        dto.setFrequencyResponse("20000");
        dto.setNumberOfCoils("4");
        dto.setImpedance("16");
        dto.setPowerHandling("32000");
        dto.setCoilHeight("10");
        dto.setCoilLayers("20");
        dto.setMagnetSize("5000");
        dto.setVas("1000");
        dto.setXmax("100");
        dto.setQms("30");
        dto.setQes("10");
        dto.setQts("10");
        dto.setSd("2000");
        dto.setBl("150");
        dto.setMms("3000");
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        dto.setImages(images);
        return dto;
    }

    @Test
    public void testValidDTO() {
        AddSubwooferDTO dto = createValidDTO();
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setPrice("-1");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setBrand("");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setModel("mo");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSensitivity() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setSensitivity("151");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{sensitivity.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSize() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setSize("51");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{size.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyResponse() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setFrequencyResponse("20001");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{frequencyResponse.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfCoils() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setNumberOfCoils("5");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{numberOfCoils.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setImpedance("17");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerHandling() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setPowerHandling("32001");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{powerHandling.max32000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCoilHeight() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setCoilHeight("11");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{coilHeight.max10}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCoilLayers() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setCoilLayers("21");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{coilLayers.max20}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidMagnetSize() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setMagnetSize("5001");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{magnetSize.max5000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidVas() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setVas("1001");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{vas.max1000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidXmax() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setXmax("101");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{xmax.max100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQms() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setQms("31");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{qms.max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQes() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setQes("11");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{qes.max10}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQts() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setQts("11");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{qts.max10}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSd() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setSd("2001");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{sd.max2000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBl() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setBl("151");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{bl.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidMms() {
        AddSubwooferDTO dto = createValidDTO();
        dto.setMms("3001");
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{mms.max3000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        AddSubwooferDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        dto.setImages(images);
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        AddSubwooferDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("");
        dto.setImages(images);
        Set<ConstraintViolation<AddSubwooferDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}