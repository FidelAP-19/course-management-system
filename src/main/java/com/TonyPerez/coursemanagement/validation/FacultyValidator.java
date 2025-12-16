package com.TonyPerez.coursemanagement.validation;

import com.TonyPerez.coursemanagement.domain.Faculty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FacultyValidator implements ConstraintValidator<ValidFaculty, Faculty> {

    @Override
    public void initialize(ValidFaculty constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(Faculty faculty, ConstraintValidatorContext context) {
        if (faculty == null) {
            return true;
        }

        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        // Validate name
        if (faculty.getName() == null || faculty.getName().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Faculty name cannot be empty")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate birthYear (1900-1995 for faculty - different from students!)
        if (faculty.getBirthYear() < 1900 || faculty.getBirthYear() > 1995) {
            context.buildConstraintViolationWithTemplate("Faculty birth year must be between 1900 and 1995")
                    .addPropertyNode("birthYear")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate deptName
        if (faculty.getDeptName() == null || faculty.getDeptName().trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Faculty Department cannot be empty")
                    .addPropertyNode("deptName")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}