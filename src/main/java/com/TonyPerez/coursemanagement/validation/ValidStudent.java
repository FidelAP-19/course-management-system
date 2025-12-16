package com.TonyPerez.coursemanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentValidator.class)
@Documented
public @interface ValidStudent {
    String message() default "Invalid student data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}