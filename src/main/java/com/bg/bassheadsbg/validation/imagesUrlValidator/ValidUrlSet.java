package com.bg.bassheadsbg.validation.imagesUrlValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImagesUrlsValidator.class)
@Documented
public @interface ValidUrlSet {
    String message() default "Invalid URL in the set!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}