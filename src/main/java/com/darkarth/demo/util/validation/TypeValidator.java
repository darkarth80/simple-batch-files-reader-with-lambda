package com.darkarth.demo.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.darkarth.demo.exception.UncheckedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeValidator implements ConstraintValidator<TypeValidation, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeValidator.class);

    @Override
    public void initialize(TypeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = true;
        
        try {
            
        } catch (UncheckedException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getLocalizedMessage()).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
    
}