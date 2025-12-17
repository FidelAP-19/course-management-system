package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.domain.Faculty;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import com.TonyPerez.coursemanagement.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/faculty")
public class FacultyRestController {

    private final FacultyRepository facultyRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public FacultyRestController(FacultyRepository facultyRepository, CourseRepository courseRepository) {
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Faculty> getFaculty(
            @RequestParam(required = false)
            @Size(min = 1,max = 50, message = "Department must be 1-50 characters")
            String deptName,
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

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Integer id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isPresent()) {
            return ResponseEntity.ok(faculty.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createFaculty(@Valid @RequestBody Faculty faculty) {

        Faculty saved = facultyRepository.save(faculty);
        return ResponseEntity.status(201).body(saved);
    }


    @PostMapping("/{id}/courses")
    public ResponseEntity<Object> addCoursesToFaculty(@PathVariable Integer id, @RequestBody List <Course> courses){
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Integer id){
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if(faculty.isPresent()){
            facultyRepository.delete(faculty.get());
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}