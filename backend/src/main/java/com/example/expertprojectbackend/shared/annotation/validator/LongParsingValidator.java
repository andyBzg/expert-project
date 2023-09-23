package com.example.expertprojectbackend.shared.annotation.validator;

import com.example.expertprojectbackend.shared.annotation.LongValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class LongParsingValidator implements ConstraintValidator<LongValidator, String> {

    @Override
    public void initialize(LongValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        try {
            return Long.parseLong(s)<0;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
