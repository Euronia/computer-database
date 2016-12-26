package com.excilys.formation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateValidator implements ConstraintValidator<Date, String> {

    
    @Autowired
    private MessageSource messageSource ;
    
    @Override
    public void initialize(Date constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String format = messageSource.getMessage("date.format", null, LocaleContextHolder.getLocale());
        
        if (value == null || "".equals(value) || GenericValidator.isDate(value, format, true)) {
            return true;
        }
        return false;
    }
}