package com.lbd.projectlbd.validator.NotNullAtLeastOne;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullAtLeastOneValidator implements ConstraintValidator<NotNullAtLeastOne, Object> {

    private String[] fieldNames;

    @Override public void initialize(NotNullAtLeastOne constraintAnnotation) {
        this.fieldNames = constraintAnnotation.fieldNames();
    }

    @Override public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        // Cos sie namieszalo
        if (object == null)
            throw new IllegalArgumentException("@NotNullAtLeastOne requires valid object");

        // Bierzemy obiekt
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);

        //
        for (String fieldName : fieldNames) {
            // Czy pole podane w @NotNullAtLeastOne(pole) istnieje
            if (!beanWrapper.isReadableProperty(fieldName))
                throw new IllegalArgumentException("@NotNullAtLeastOne requires valid field");
            // Wartosc
            Object fieldValue = beanWrapper.getPropertyValue(fieldName);
            // jak wszystkie pola null to isValid nie przeszlo
            // jak chociaz jedno nie null to git
            if (fieldValue != null)
                return true;

            // Dodajemy pola null do bledu (jak wszystko null to je wyswietli)
            // Wylaczamy domyslny error (bo pokazuje tylko nazwe klasy a nazwa field jest null wiec useless)
            constraintValidatorContext.disableDefaultConstraintViolation();
            // Wylaczamy domyslny error (bo pokazuje tylko nazwe klasy a nazwa field jest null wiec useless)
            constraintValidatorContext.disableDefaultConstraintViolation();
            // Dodajemy pola ktore sa null
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName).addConstraintViolation();
        }

        // Jak nie przeszlo validacji to zwracamy false, a pola ktore dodalismy do constraintValidatorContext
        // zostana pokazane w bledzie (obsluga bledu w ControllersAdvice.java)
        return false;
    }


}
