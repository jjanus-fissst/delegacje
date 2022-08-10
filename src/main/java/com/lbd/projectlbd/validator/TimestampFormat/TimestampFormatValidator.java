package com.lbd.projectlbd.validator.TimestampFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;

public class TimestampFormatValidator implements ConstraintValidator<TimestampFormat, Timestamp> {


    @Override public void initialize(TimestampFormat constraintAnnotation) { }

    @Override
    public boolean isValid(Timestamp timestamp, ConstraintValidatorContext constraintValidatorContext) {
//        try {
//            Timestamp.valueOf()
//        }

        return true;

    }
}
