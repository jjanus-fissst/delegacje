package com.lbd.projectlbd.validator.IsValidCountry;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class IsValidCountryValidator implements ConstraintValidator<IsValidCountry, String> {

    @Override public void initialize(IsValidCountry constraintAnnotation) { }

    @Override public boolean isValid(String countryCode, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(Locale.getISOCountries()).contains(countryCode);
    }
}
