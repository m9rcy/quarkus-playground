package io.m9rcy.playground.web.validation.validator;


import io.m9rcy.playground.web.validation.constraint.FieldMustBeValidEnumValue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMustBeValidEnumValueValidator implements ConstraintValidator <FieldMustBeValidEnumValue,String> {

    private List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return null != value && valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(FieldMustBeValidEnumValue constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        Enum[] enumValArr = enumClass.getEnumConstants();

        for(Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }

    }
}