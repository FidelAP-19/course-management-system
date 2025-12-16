package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseRestController(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @GetMapping("/{dept}/{num}")
    public ResponseEntity<Course> getCourseByDeptAndNum(@PathVariable String dept,@PathVariable int num){
        Course course = courseRepository.findByCourseDeptAndCourseNum(dept, num);

        if(course != null) {
            return ResponseEntity.ok(course);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@Valid @RequestBody Course course){

        Course existing = courseRepository.findByCourseDeptAndCourseNum(
                course.getCourseDept(),
                course.getCourseNum()
        );

        if (existing != null) {
            boolean exactMatch = (
                    existing.getCourseNum() == course.getCourseNum() &&
                            existing.getCourseDept().equals(course.getCourseDept()) &&
                            existing.getNumCredits() == course.getNumCredits() &&
                            existing.isGraduateCourse() == course.isGraduateCourse()
            );

            if (exactMatch) {
                // Idempotent - return existing with 200 OK
                return ResponseEntity.ok(existing);
            } else {
                // Conflict - different data
                return ResponseEntity.status(409).body(
                        "Course " + course.getCourseDept() + "-" + course.getCourseNum() +
                                " already exists with different values"
                );
            }
        }


        Course saved = courseRepository.save(course);
        return ResponseEntity.status(201).body(saved);  // 201 Created
    }

    @DeleteMapping("/{dept}/{num}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String dept, @PathVariable int num) {
        Course courseToDelete = courseRepository.findByCourseDeptAndCourseNum(dept, num);

        if(courseToDelete != null){
            courseRepository.delete(courseToDelete);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}