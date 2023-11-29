package com.crocobet.usermanagement.exception.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {StrongPasswordValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Password is not strong enough, include at least 1 uppercase, 1 number and 1 special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}