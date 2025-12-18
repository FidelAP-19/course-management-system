package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.domain.Faculty;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import com.TonyPerez.coursemanagement.repository.FacultyRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/faculty")
@Tag(name = "Faculty Management", description = "APIs for managing faculty members and course assignments")
public class FacultyRestController {

    private final FacultyRepository facultyRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public FacultyRestController(FacultyRepository facultyRepository, CourseRepository courseRepository) {
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
    }

    @Operation(
            summary = "Get all faculty or filter by criteria",
            description = "Returns a list of faculty members. Can optionally filter by department and/or tenure status."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved faculty"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters")
    })
    @GetMapping
    public List<Faculty> getFaculty(
            @Parameter(description = "Filter by department name (1-50 characters)")
            @RequestParam(required = false)
            @Size(min = 1,max = 50, message = "Department must be 1-50 characters")
            String deptName,
            @Parameter(description = "Filter by tenure status (true/false)")
            @RequestParam(required = false)
            Boolean isTenured
            ) {
        if (deptName != null && isTenured != null){
            return facultyRepository.findByDeptNameAndIsTenured(deptName, isTenured);
        } else if (deptName != null) {
            return facultyRepository.findByDeptName(deptName);
        } else if (isTenured != null) {
            return facultyRepository.findByIsTenured(isTenured);
        } else{
            return facultyRepository.findAll();
        }
    }
    @Operation(
            summary = "Get faculty by ID",
            description = "Returns a faculty member by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Faculty by ID"),
            @ApiResponse(responseCode = "404", description = "Faculty not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(
            @Parameter(description = "Faculty ID to retrieve")
            @PathVariable Integer id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isPresent()) {
            return ResponseEntity.ok(faculty.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create a new faculty",
            description = "Creates a new faculty member with validation"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Faculty created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid student data (validation failed)")
    })
    @PostMapping
    public ResponseEntity<Object> createFaculty(
            @Parameter(description = "Faculty object to create")
            @Valid @RequestBody Faculty faculty) {

        Faculty saved = facultyRepository.save(faculty);
        return ResponseEntity.status(201).body(saved);
    }

    @Operation(
            summary = "Add courses to faculty",
            description = "Added courses to a faculty members record by their faculty ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses added to Faculty"),
            @ApiResponse(responseCode = "404", description = "Faculty not found")
    })
    @PostMapping("/{id}/courses")
    public ResponseEntity<Object> addCoursesToFaculty(
            @Parameter(description = "Faculty Id to add course")
            @PathVariable Integer id,
            @Parameter(description = "List of courses to add to Faculty")
            @RequestBody List <Course> courses){
        Optional<Faculty> facultyObject = facultyRepository.findById(id);

        if(!facultyObject.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Faculty faculty = facultyObject.get();

        for(Course course: courses){
            Course existingCourse = courseRepository.findByCourseDeptAndCourseNum(
                    course.getCourseDept(),
                    course.getCourseNum()
            );
            if (existingCourse == null){
                return ResponseEntity.badRequest().body(
                        "Course " + course.getCourseDept() + "-" + course.getCourseNum() + " not found"
                );
            }
            boolean alreadyTeaching = false;
            for (int i = 0; i < faculty.getNumCoursesTaught();i++){
                if (faculty.getCourseTaught(i).equals(existingCourse)) {
                    alreadyTeaching = true;
                    break;
                }
            }
            if (!alreadyTeaching) {
                faculty.addCourseTaught(existingCourse);
            }
        }

        Faculty saved = facultyRepository.save(faculty);
        return ResponseEntity.ok(saved);
    }

    @Operation(
            summary = "Delete faculty by ID",
            description = "Deletes a faculty record by their faculty ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "faculty deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Faculty not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(
            @Parameter(description = "Faculty ID to delete")
            @PathVariable Integer id){
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if(faculty.isPresent()){
            facultyRepository.delete(faculty.get());
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}