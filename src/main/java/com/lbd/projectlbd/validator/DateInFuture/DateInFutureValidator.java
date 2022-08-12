package com.lbd.projectlbd.validator.DateInFuture;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateInFutureValidator implements ConstraintValidator<DateInFuture, LocalDate> {

    @Override public void initialize(DateInFuture constraintAnnotation) { }

    @Override public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        // todo nazwa w ((ConstraintValidatorContextImpl) constraintValidatorContext).getConstraintViolationCreationContexts().get(0).getPath()
        return localDate == null || !localDate.isBefore(LocalDate.now());
    }

}
