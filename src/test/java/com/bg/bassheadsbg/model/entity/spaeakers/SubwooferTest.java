package com.bg.bassheadsbg.model.entity.spaeakers;

import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
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
public class SubwooferTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Subwoofer createValidSubwoofer() {
        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);
        subwoofer.setPrice(49999);
        subwoofer.setBrand("BrandName");
        subwoofer.setModel("ModelName");
        subwoofer.setSensitivity(100);
        subwoofer.setSize(45);
        subwoofer.setFrequencyResponse(20000);
        subwoofer.setNumberOfCoils((byte) 4);
        subwoofer.setImpedance(16);
        subwoofer.setPowerHandling((short) 32000);
        subwoofer.setBl(1f);
        subwoofer.setMms(320);
        subwoofer.setQes(0.5f);
        subwoofer.setQms(0.3f);
        subwoofer.setQts(0.6f);
        subwoofer.setSd(430);
        subwoofer.setVas(21);
        subwoofer.setXmax((byte) 50);
        subwoofer.setCoilHeight(10);
        subwoofer.setCoilLayers((byte) 2);
        subwoofer.setMagnetSize((short) 300);
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        subwoofer.setImages(images);
        return subwoofer;
    }

    @Test
    public void testValidSubwoofer() {
        Subwoofer subwoofer = createValidSubwoofer();
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setPrice(-1);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBrand() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setBrand("");
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{brand.notBlank}"));
        assertTrue(violationMessages.contains("{brand.min3max15}"));
    }

    @Test
    public void testInvalidModel() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setModel("mo");
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{model.min3max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSensitivity() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setSensitivity(151);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{sensitivity.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSize() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setSize(51);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{size.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFrequencyResponse() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setFrequencyResponse(20001);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{frequencyResponse.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfCoils() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setNumberOfCoils((byte) 5);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{numberOfCoils.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setImpedance(17);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerHandling() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setPowerHandling((short) 32001);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{powerHandling.max32000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        Subwoofer subwoofer = createValidSubwoofer();
        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        subwoofer.setImages(images);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        Subwoofer subwoofer = createValidSubwoofer();
        List<String> images = new ArrayList<>();
        images.add("");
        subwoofer.setImages(images);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testInvalidMms() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setMms(3200f);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{mms.max3000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidBl() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setBl(151);
        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{bl.max150}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQes() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setQes(11f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{qes.max10}", violations.iterator().next().getMessageTemplate());
    }
    @Test
    public void testInvalidQesTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
//        in the createValidSubwoofer(), the setQes was 0.5f initially.
        subwoofer.setQes(subwoofer.getQes() - 0.6f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{qes.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQms() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setQms(31f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{qms.max30}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQmsTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setQms(subwoofer.getQms() - 100.6f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{qms.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQts() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setQts(11f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{qts.max10}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidQtsTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setQts(subwoofer.getQts() - 150.6f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{qts.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidVas() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setVas(1500f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{vas.max1000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidVasTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setVas(-30000.56f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{vas.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSd() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setSd(-30000.56f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{sd.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setSd(50000.3535f);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{sd.max2000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidXmax() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setXmax((byte) 120);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{xmax.max100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidXmaxTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setXmax((byte) 0);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{xmax.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidXmaxThree() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setXmax((byte) -1);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{xmax.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCoilHeight() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setCoilHeight(11);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{coilHeight.max10}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCoilHeightTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setCoilHeight(-1);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{coilHeight.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCoilLayers() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setCoilLayers((byte) 21);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{coilLayers.max20}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCoilLayersTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setCoilLayers((byte) -1);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{coilLayers.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidMagnetSize() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setMagnetSize((short) 5001);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{magnetSize.max5000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidMagnetSizeTwo() {
        Subwoofer subwoofer = createValidSubwoofer();
        subwoofer.setMagnetSize((short) -1);

        Set<ConstraintViolation<Subwoofer>> violations = validator.validate(subwoofer);
        assertEquals(1, violations.size());
        assertEquals("{magnetSize.positive}", violations.iterator().next().getMessageTemplate());
    }
}
