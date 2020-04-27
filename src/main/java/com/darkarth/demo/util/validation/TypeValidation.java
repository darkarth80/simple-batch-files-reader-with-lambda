package com.darkarth.demo.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation for custom constraint validation Type.
 * 
 */
@Documented
@Constraint(validatedBy = TypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeValidation {

    String message() default "The Type must be one of the following: Simple, Complex";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}