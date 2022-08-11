package com.lbd.projectlbd.validator.DateInFuture;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateInFutureValidator implements ConstraintValidator<DateInFuture, LocalDate> {

    @Override public void initialize(DateInFuture constraintAnnotation) { }

    @Override public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate == null || !localDate.isBefore(LocalDate.now());
    }

}
