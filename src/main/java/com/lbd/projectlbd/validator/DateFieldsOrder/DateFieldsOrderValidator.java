package com.lbd.projectlbd.validator.DateFieldsOrder;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateFieldsOrderValidator implements ConstraintValidator<DateFieldsOrder, Object> {

    private boolean required;
    private String dateField;
    private String dateFieldAfter;

    @Override public void initialize(DateFieldsOrder constraintAnnotation) {
        this.dateField = constraintAnnotation.dateField();
        this.dateFieldAfter = constraintAnnotation.dateFieldAfter();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        // Cos sie namieszalo
        if (object == null)
            throw new IllegalArgumentException("@DateFieldsCheck requires valid object");

        // Bierzemy obiekt
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);

        //
        if (!beanWrapper.isReadableProperty(dateFieldAfter) || !beanWrapper.isReadableProperty(dateField))
            throw new IllegalArgumentException("@DateFieldsCheck requires valid fields");

        // Wartosc
        Object dateFieldValue = beanWrapper.getPropertyValue(dateField);
        Object dateFieldAfterValue = beanWrapper.getPropertyValue(dateFieldAfter);

        // Jak sa null, ale nie wymagamy ich zeby byly (chcemy sprawdzac tylko wtedy kiedy oba istnieja)
        if (!required) {
            if (dateFieldAfterValue == null || dateFieldValue == null)
                return true;
        }

        // Jak wymagamy to sprawdzamy warunki
        if (dateFieldAfterValue == null || dateFieldValue == null)
            throw new IllegalArgumentException("@DateFieldsCheck requires not null fields");

        if (!(dateFieldAfterValue instanceof LocalDate) || !(dateFieldValue instanceof LocalDate))
            throw new IllegalArgumentException("@DateFieldsCheck requires fields of type LocalDate");


        return !((LocalDate) dateFieldAfterValue).isBefore((LocalDate) dateFieldValue);
    }
}
