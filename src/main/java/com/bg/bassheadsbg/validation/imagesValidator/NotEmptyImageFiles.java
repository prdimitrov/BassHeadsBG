package com.bg.bassheadsbg.validation.imagesValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyImageFilesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyImageFiles {
    String message() default "At least one image must be uploaded";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}