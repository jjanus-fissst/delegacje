package com.lbd.projectlbd.validator.TimestampFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.util.Date;

public class TimestampFormatValidator implements ConstraintValidator<TimestampFormat, Timestamp> {

    private boolean shouldBeInFuture;
    @Override public void initialize(TimestampFormat constraintAnnotation) {
        this.shouldBeInFuture = constraintAnnotation.shouldBeInFuture();
    }

    @Override
    public boolean isValid(Timestamp timestamp, ConstraintValidatorContext constraintValidatorContext) {

        boolean isOk = true;

        if (shouldBeInFuture && timestamp.before(new Date())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("The delegation cannot include the start date as a past date.")
                    .addConstraintViolation();
            isOk = false;
        }

        return isOk;
    }
}
