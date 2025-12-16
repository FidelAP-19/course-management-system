package com.TonyPerez.coursemanagement.validation;

import com.TonyPerez.coursemanagement.domain.Student;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentValidator implements ConstraintValidator<ValidStudent, Student> {

    @Override
    public void initialize(ValidStudent constraintAnnotation) {

    }

    @Override
    public boolean isValid(Student student, ConstraintValidatorContext context) {
        if (student == null) {
            return true;
        }

        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        // Validate name
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Student name cannot be empty")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate birthYear (1900-2015 for students)
        if (student.getBirthYear() < 1900 || student.getBirthYear() > 2015) {
            context.buildConstraintViolationWithTemplate("Birth year must be between 1900 and 2015")
                    .addPropertyNode("birthYear")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate major
        if (student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Major cannot be empty")
                    .addPropertyNode("major")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}