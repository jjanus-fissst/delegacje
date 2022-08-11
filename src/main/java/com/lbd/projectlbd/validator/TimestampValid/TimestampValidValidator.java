package com.lbd.projectlbd.validator.TimestampValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class TimestampValidValidator implements ConstraintValidator<TimestampValid, LocalDate> {

    private boolean shouldBeInFuture;
    @Override public void initialize(TimestampValid constraintAnnotation) {
        this.shouldBeInFuture = constraintAnnotation.shouldBeInFuture();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {

        boolean isOk = true;

        if (shouldBeInFuture && localDate != null && localDate.isBefore(LocalDate.now())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("The delegation cannot include the start date as a past date.")
                    .addConstraintViolation();
            isOk = false;
        }

        return isOk;
    }
}
