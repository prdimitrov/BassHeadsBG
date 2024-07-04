package com.bg.bassheadsbg.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UserExistValidator.class)
public @interface UserExist {
    String message() default "User does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
