package com.lbd.projectlbd.validator.DateInFuture;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateInFutureValidator.class)
public @interface DateInFuture {

    public String message() default "The date shouldn't be in past.";   // Error message
    public Class<?>[] groups() default {};                              // Group of constraints
    public Class<? extends Payload>[] payload() default {};             // Additional information about annotation

}
