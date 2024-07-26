package com.bg.bassheadsbg.model.entity.amplifiers;

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
public class MultiChannelAmplifierTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public MultiChannelAmplifier validMultiChannelAmplifier() {
        MultiChannelAmplifier multiChannelAmplifier = new MultiChannelAmplifier();
        multiChannelAmplifier.setId(1L);
        multiChannelAmplifier.setPrice(49999);
        multiChannelAmplifier.setBrand("BrandName");
        multiChannelAmplifier.setModel("ModelName");
        multiChannelAmplifier.setImpedance(0.5f);
        multiChannelAmplifier.setPower(3000);
        multiChannelAmplifier.setRemoteControl(true);
        multiChannelAmplifier.setLowInputLevel("Yes");
        multiChannelAmplifier.setHighInputLevel("No");
        multiChannelAmplifier.setHighPassFilter("");
        multiChannelAmplifier.setLowPassFilter("");
        multiChannelAmplifier.setSubsonicFilter("");
        multiChannelAmplifier.setBassBoost(true);
        multiChannelAmplifier.setAmplifierClass(AmpClass.D);
        multiChannelAmplifier.setDistortion(5.2f);
        multiChannelAmplifier.setCurrentDraw(20000);
        multiChannelAmplifier.setFuseRating((short) 20000);
        multiChannelAmplifier.setHeight((short) 30000);
        multiChannelAmplifier.setWidth((short) 30000);
        multiChannelAmplifier.setLength((short) 30000);
        multiChannelAmplifier.setNumberOfChannels((byte) 16);
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        multiChannelAmplifier.setImages(images);
        return multiChannelAmplifier;
    }

    @Test
    public void testValidMultiChannelAmplifier() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setPrice(-1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPriceTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setPrice(50001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{price.max50000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setImpedance(0);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{impedance.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedanceTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setImpedance(-1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{impedance.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedanceThree() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setImpedance(17);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPower() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setPower(300001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{power.max300000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setPower(-1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{power.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerThree() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setPower(0);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{power.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevel() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHighInputLevel("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.max100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevelTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHighInputLevel("                                 ");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevelThree() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHighInputLevel(" ");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevelFour() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHighInputLevel(null);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }


    @Test
    public void testInvalidLowInputlevel() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLowInputLevel("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.max100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputlevelTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLowInputLevel("                                 ");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputlevelThree() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLowInputLevel(" ");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputlevelFour() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLowInputLevel(null);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSubsonicInputlevel() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setSubsonicFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{subsonicFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighPassFilter() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHighPassFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowPassFilter() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLowPassFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSubsonicFilter() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setSubsonicFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{subsonicFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidAmplifierClass() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setAmplifierClass(null);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{amplifierClass.notNull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDistortion() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setDistortion(50.1f);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{distortion.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDistortionTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setDistortion(-1.1f);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{distortion.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCurrentDraw() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setCurrentDraw(20001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{currentDraw.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCurrentDrawTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setCurrentDraw(0);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{currentDraw.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRating() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setFuseRating((short) 20001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRatingTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setFuseRating((short) 0);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRatingThree() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setFuseRating((short) -1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHeight() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHeight((short) -1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{height.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHeightTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setHeight((short) 30001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{height.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLength() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLength((short) -1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{length.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLengthTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setLength((short) 30001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{length.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidWidth() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setWidth((short) -1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{width.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidWidthTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setWidth((short) 30001);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{width.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();

        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        multiChannelAmplifier.setImages(images);
        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();

        List<String> images = new ArrayList<>();
        images.add("");
        multiChannelAmplifier.setImages(images);
        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testNumberOfChannels() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setNumberOfChannels((byte) 17);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfChannels.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testNumberOfChannelsTwo() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setNumberOfChannels((byte) 0);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfChannels.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testNumberOfChannelsThree() {
        MultiChannelAmplifier multiChannelAmplifier = validMultiChannelAmplifier();
        multiChannelAmplifier.setNumberOfChannels((byte) -1);

        Set<ConstraintViolation<MultiChannelAmplifier>> violations = validator.validate(multiChannelAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfChannels.positive}", violations.iterator().next().getMessageTemplate());
    }
}