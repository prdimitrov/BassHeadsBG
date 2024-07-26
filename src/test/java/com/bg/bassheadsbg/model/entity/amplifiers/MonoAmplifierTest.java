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
public class MonoAmplifierTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public MonoAmplifier validMonoAmplifier() {
        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);
        monoAmplifier.setPrice(49999);
        monoAmplifier.setBrand("BrandName");
        monoAmplifier.setModel("ModelName");
        monoAmplifier.setImpedance(0.5f);
        monoAmplifier.setNumberOfRca((byte) 2);
        monoAmplifier.setPower(3000);
        monoAmplifier.setNumberOfSpeakerOutputs((byte) 2);
        monoAmplifier.setRemoteControl(true);
        monoAmplifier.setLowInputLevel("Yes");
        monoAmplifier.setHighInputLevel("No");
        monoAmplifier.setHighPassFilter("");
        monoAmplifier.setLowPassFilter("");
        monoAmplifier.setSubsonicFilter("");
        monoAmplifier.setBassBoost(true);
        monoAmplifier.setAmplifierClass(AmpClass.D);
        monoAmplifier.setDistortion(5.2f);
        monoAmplifier.setCurrentDraw(20000);
        monoAmplifier.setFuseRating((short) 20000);
        monoAmplifier.setHeight((short) 30000);
        monoAmplifier.setWidth((short) 30000);
        monoAmplifier.setLength((short) 30000);
        List<String> images = new ArrayList<>();
        images.add("http://example.com/image1.jpg");
        images.add("http://example.com/image2.jpg");
        monoAmplifier.setImages(images);
        return monoAmplifier;
    }

    @Test
    public void testValidMonoAmplifier() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPrice() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setPrice(-1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{price.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPriceTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setPrice(50001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{price.max50000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedance() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setImpedance(0);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{impedance.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedanceTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setImpedance(-1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{impedance.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImpedanceThree() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setImpedance(17);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{impedance.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfRca() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setNumberOfRca((byte) 5);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfRca.max4}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfRcaTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setNumberOfRca((byte) -1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfRca.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPower() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setPower(300001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{power.max300000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setPower(-1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{power.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidPowerThree() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setPower(0);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{power.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfSpeakerOutputs() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setNumberOfSpeakerOutputs((byte) 17);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfSpeakerOutputs.max16}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfSpeakerOutputsTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setNumberOfSpeakerOutputs((byte) 0);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfSpeakerOutputs.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidNumberOfSpeakerOutputsThree() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setNumberOfSpeakerOutputs((byte) -1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{numberOfSpeakerOutputs.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevel() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHighInputLevel("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.max100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevelTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHighInputLevel("                                 ");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevelThree() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHighInputLevel(" ");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighInputlevelFour() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHighInputLevel(null);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }


    @Test
    public void testInvalidLowInputlevel() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLowInputLevel("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.max100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputlevelTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLowInputLevel("                                 ");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputlevelThree() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLowInputLevel(" ");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowInputlevelFour() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLowInputLevel(null);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowInputLevel.notBlank}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSubsonicInputlevel() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setSubsonicFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{subsonicFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHighPassFilter() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHighPassFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{highPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLowPassFilter() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLowPassFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{lowPassFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidSubsonicFilter() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setSubsonicFilter("100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax"
                + "100symbolsmax100symbolsmax100symbolsmax100symbolsmax100symbolsmax");

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{subsonicFilter.sizeMax100}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidAmplifierClass() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setAmplifierClass(null);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{amplifierClass.notNull}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDistortion() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setDistortion(50.1f);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{distortion.max50}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidDistortionTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setDistortion(-1.1f);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{distortion.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCurrentDraw() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setCurrentDraw(20001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{currentDraw.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidCurrentDrawTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setCurrentDraw(0);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{currentDraw.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRating() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setFuseRating((short) 20001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.max20000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRatingTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setFuseRating((short) 0);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidFuseRatingThree() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setFuseRating((short) -1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{fuseRating.positive}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHeight() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHeight((short) -1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{height.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidHeightTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setHeight((short) 30001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{height.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLength() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLength((short) -1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{length.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidLengthTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setLength((short) 30001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{length.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidWidth() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setWidth((short) -1);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{width.positiveOrZero}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidWidthTwo() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();
        monoAmplifier.setWidth((short) 30001);

        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(1, violations.size());
        assertEquals("{width.max30000}", violations.iterator().next().getMessageTemplate());
    }

    @Test
    public void testInvalidImages() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();

        List<String> images = new ArrayList<>();
        images.add("invalid-url");
        monoAmplifier.setImages(images);
        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.url}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }

    @Test
    public void testEmptyImages() {
        MonoAmplifier monoAmplifier = validMonoAmplifier();

        List<String> images = new ArrayList<>();
        images.add("");
        monoAmplifier.setImages(images);
        Set<ConstraintViolation<MonoAmplifier>> violations = validator.validate(monoAmplifier);
        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toSet());
        assertTrue(violationMessages.contains("{images.notBlank}"));
        assertTrue(violationMessages.contains("{images.validUrlList}"));
    }
}