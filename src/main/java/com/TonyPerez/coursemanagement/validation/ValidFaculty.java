package com.TonyPerez.coursemanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FacultyValidator.class)
@Documented
public @interface ValidFaculty {
    String message() default "Invalid faculty data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}