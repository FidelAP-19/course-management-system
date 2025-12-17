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


@RestController
@Validated
@RequestMapping("/api/students")
public class StudentRestController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentRestController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id){
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Student> getStudents(
            @RequestParam(required = false)
            @Size(min = 2, max = 50, message = "Major must be 2-50 characters")
            String major,
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

    @PostMapping
    public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student){

        Student saved = studentRepository.save(student);
        return ResponseEntity.status(201).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id){
        Optional<Student> studentToDelete = studentRepository.findById(id);

        if(studentToDelete.isPresent()){
            studentRepository.delete(studentToDelete.get());
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}
