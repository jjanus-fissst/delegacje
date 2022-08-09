package com.lbd.projectlbd.validator.TimestampFormat;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimestampFormatValidator.class)
public @interface TimestampFormat {

    public String message() default "Invalid timestamp format";         // Error message
    public Class<?>[] groups() default {};                              // Group of constraints
    public Class<? extends Payload>[] payload() default {};             // Additional information about annotation

}
