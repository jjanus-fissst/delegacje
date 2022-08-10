package com.lbd.projectlbd.validator.IsCountry;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Locale;

public class IsCountryValidator implements ConstraintValidator<IsCountry, String> {

    @Override public void initialize(IsCountry constraintAnnotation) { }

    @Override public boolean isValid(String countryCode, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(Locale.getISOCountries()).contains(countryCode);
    }
}
