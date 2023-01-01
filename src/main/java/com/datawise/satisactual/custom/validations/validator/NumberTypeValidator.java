package com.datawise.satisactual.custom.validations.validator;

import com.datawise.satisactual.custom.validations.annotation.NumberType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberTypeValidator implements ConstraintValidator<NumberType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return true;
    }
}
