package com.lbd.projectlbd.validator.TimestampValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.util.Date;

public class TimestampValidValidator implements ConstraintValidator<TimestampValid, Timestamp> {

    private boolean shouldBeInFuture;
    @Override public void initialize(TimestampValid constraintAnnotation) {
        this.shouldBeInFuture = constraintAnnotation.shouldBeInFuture();
    }

    @Override
    public boolean isValid(Timestamp timestamp, ConstraintValidatorContext constraintValidatorContext) {

        boolean isOk = true;

        if (shouldBeInFuture && timestamp != null && timestamp.before(new Date())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("The delegation cannot include the start date as a past date.")
                    .addConstraintViolation();
            isOk = false;
        }

        return isOk;
    }
}
