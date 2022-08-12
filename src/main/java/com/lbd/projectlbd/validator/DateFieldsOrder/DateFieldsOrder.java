package com.lbd.projectlbd.validator.DateFieldsOrder;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFieldsOrderValidator.class)
public @interface DateFieldsOrder {

    public String message() default "Wrong dates order";             // Error message
    public Class<?>[] groups() default {};                           // Group of constraints
    public Class<? extends Payload>[] payload() default {};          // Additional information about annotation

    public boolean required() default true;                          // (chcemy sprawdzac tylko wtedy kiedy oba istnieja)
    public String dateField();
    public String dateFieldAfter();

}
