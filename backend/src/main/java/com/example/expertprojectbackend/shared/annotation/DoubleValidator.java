package com.example.expertprojectbackend.shared.annotation;

import com.example.expertprojectbackend.shared.annotation.validator.DoubleParsingValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleParsingValidator.class)
public @interface DoubleValidator {

    String message() default "the value must be double number";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
