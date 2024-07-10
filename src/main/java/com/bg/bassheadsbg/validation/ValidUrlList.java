package com.bg.bassheadsbg.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListUrlsValidator.class)
@Documented
public @interface ValidUrlList {
    String message() default "Invalid URL in the list!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}