package com.example.expertprojectbackend.annotation.validator;

import com.example.expertprojectbackend.annotation.DoubleValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleParsingValidator implements ConstraintValidator<DoubleValidator, String> {
    @Override
    public void initialize(DoubleValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
            try {
                return Double.parseDouble(s)<0;
            } catch (NumberFormatException e) {
                return false;
            }
    }
}
