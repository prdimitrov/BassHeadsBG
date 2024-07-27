package com.bg.bassheadsbg.model.dto.add;

import com.bg.bassheadsbg.model.enums.AmpClass;
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
public class AddMonoAmpDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AddMonoAmpDTO createValidDTO() {
        AddMonoAmpDTO dto = new AddMonoAmpDTO();
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
        dto.setNumberOfRca("4");
        dto.setNumberOfSpeakerOutputs("16");
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
        AddMonoAmpDTO dto = createValidDTO();
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setPrice("-1");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setBrand("");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setModel("mo");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidAmplifierClass() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setAmplifierClass(null);
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{amplifierClass.notNull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setImpedance("17");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPower() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setPower("300001");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{power.max300000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighPassFilter() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setHighPassFilter("a".repeat(101));
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{highPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowPassFilter() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setLowPassFilter("a".repeat(101));
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{lowPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSubsonicFilter() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setSubsonicFilter("a".repeat(101));
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{subsonicFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputLevel() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setLowInputLevel("");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputLevel() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setHighInputLevel("");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDistortion() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setDistortion("51");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{distortion.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCurrentDraw() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setCurrentDraw("20001");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{currentDraw.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRating() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setFuseRating("20001");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfRca() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setNumberOfRca("5");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{numberOfRca.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfSpeakerOutputs() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setNumberOfSpeakerOutputs("17");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{numberOfSpeakerOutputs.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHeight() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setHeight("30001");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{height.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidWidth() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setWidth("30001");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{width.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLength() {
        AddMonoAmpDTO dto = createValidDTO();
        dto.setLength("30001");
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("{length.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        AddMonoAmpDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        dto.setImages(images);
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        AddMonoAmpDTO dto = createValidDTO();
        List<String> images = new ArrayList<>();
        images.add("");
        dto.setImages(images);
        Set<ConstraintViolation<AddMonoAmpDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}
