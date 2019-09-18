package com.adamkorzeniak.masterdata.annotation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

	List<String> valueList = null;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!valueList.contains(value.toUpperCase())) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(EnumValidator constraintAnnotation) {
		valueList = new ArrayList<String>();
		Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

		Enum[] enumValArr = enumClass.getEnumConstants();
		for (Enum enumVal : enumValArr) {
			valueList.add(enumVal.toString().toUpperCase());
		}

	}
}
