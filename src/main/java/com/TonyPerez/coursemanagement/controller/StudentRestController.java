package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.TonyPerez.coursemanagement.domain.Student;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Validated
@RequestMapping("/api/students")
@CrossOrigin(origins = {
        "https://profound-embrace-production.up.railway.app",
        "http://localhost:3000"  // For local development
})
@Tag(name = "Student Management", description = "APIs for managing student records")
public class StudentRestController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentRestController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Operation(
            summary = "Get all students or filter by criteria",
            description = "Returns a list of students. Can optionally filter by major and/or birth year."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved students"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters")
    })

    @GetMapping
    public List<Student> getStudents(
            @Parameter(description = "Filter by student major (2-50 characters)")
            @RequestParam(required = false)
            @Size(min = 2, max = 50, message = "Major must be 2-50 characters")
            String major,
            @Parameter(description = "Filter by birth year (1900-2015)")
            @RequestParam(required = false)
            @Min(value = 1900, message = "Birth Year cannot be less than 1900")
            @Max(value = 2015, message = "Birth Year cannot be greater than 2015")
            Integer birthYear
    ) {
        if (major != null && birthYear != null) {
            return studentRepository.findByMajorAndBirthYear(major, birthYear);
        } else if (major != null) {
           return studentRepository.findByMajor(major);
        }else if (birthYear != null){
            return studentRepository.findByBirthYear(birthYear);
        }
        else {
            return studentRepository.findAll();
        }
    }

    @Operation(
            summary = "Get student by ID",
            description = "Returns a single student by their student ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "Student ID to retrieve")
            @PathVariable Integer id){
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create a new student",
            description = "Creates a new student record with validation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid student data (validation failed)")
    })
    @PostMapping
    public ResponseEntity<Object> createStudent(
            @Parameter(description = "Student object to create")
            @Valid @RequestBody Student student){

        Student saved = studentRepository.save(student);
        return ResponseEntity.status(201).body(saved);
    }

    @Operation(
            summary = "Delete student by ID",
            description = "Deletes a student record by their student ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Student ID to delete")
            @PathVariable Integer id){
        Optional<Student> studentToDelete = studentRepository.findById(id);

        if(studentToDelete.isPresent()){
            studentRepository.delete(studentToDelete.get());
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}
