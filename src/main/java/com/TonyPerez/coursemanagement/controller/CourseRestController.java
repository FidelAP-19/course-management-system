package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Management", description = "APIs for managing course catalog")
public class CourseRestController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseRestController(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Operation(
            summary = "Get all courses",
            description= "Returns list of all courses"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all courses"),
            @ApiResponse(responseCode = "400", description = "Courses not found")
    })
    @GetMapping
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @Operation(
            summary = "Get courses by dept name and course number",
            description= "Returns list of courses. Filtered by department name and course number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses found"),
            @ApiResponse(responseCode = "404", description = "Courses not found")
    })
    @GetMapping("/{dept}/{num}")
    public ResponseEntity<Course> getCourseByDeptAndNum(
            @Parameter(description = "Filter by department (1-50 characters)")
            @PathVariable String dept,
            @Parameter(description = "Filter by course number (100-999 range)")
            @PathVariable int num){
        Course course = courseRepository.findByCourseDeptAndCourseNum(dept, num);

        if(course != null) {
            return ResponseEntity.ok(course);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(
            summary = "Create courses",
            description = "Creates a new course with validation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existing course"),
            @ApiResponse(responseCode = "201", description = "Course created"),
            @ApiResponse(responseCode = "409", description = "Invalid course data (validation failed)")
    })
    @PostMapping
    public ResponseEntity<Object> createCourse(
            @Parameter(description = "Course object to create")
            @Valid @RequestBody Course course){

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
    @Operation(
            summary = "Delete course by department name and course number",
            description = "Deletes course by the course department name and course number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
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