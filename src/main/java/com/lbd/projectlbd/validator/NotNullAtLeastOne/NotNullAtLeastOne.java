package com.lbd.projectlbd.validator.NotNullAtLeastOne;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})                                     // @NotNullAtLeastOne - oczekuje adnotacji nad klassa (np. ElementType.FIELD to by nad polem czyli nad zmienna)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullAtLeastOneValidator.class)     // podpinamy klase
public @interface NotNullAtLeastOne {

    public String message() default "At least one field must be set";   // Error message
    public Class<?>[] groups() default {};                              // Group of constraints
    public Class<? extends Payload>[] payload() default {};             // Additional information about annotation

    public String[] fieldNames();                                       // My fields I want NotNullAtLeastOne

}
