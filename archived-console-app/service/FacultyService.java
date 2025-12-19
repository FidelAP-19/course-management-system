package com.TonyPerez.coursemanagement.service;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.domain.Faculty;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import com.TonyPerez.coursemanagement.repository.FacultyRepository;
import java.util.ArrayList;
import java.util.List;

public class FacultyService {
    private FacultyRepository facultyRepository;
    private CourseRepository courseRepository;

    public FacultyService(FacultyRepository facultyRepo, CourseRepository courseRepo) {
        this.facultyRepository = facultyRepo;
        this.courseRepository = courseRepo;
    }

    public List<Course> addCoursesToFaculty(int facultyId, List<Course> courses) {
        ArrayList<Course> addedCourses = new ArrayList<>();


        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));


        for(Course course : courses) {

            Course existingCourse = courseRepository
                    .findByCourseDeptAndCourseNum(course.getCourseDept(), course.getCourseNum());

            if (existingCourse == null) {
                throw new IllegalArgumentException("Course " + course.getCourseName() + " not found");
            }

            // Check for duplicate
            boolean alreadyTeaching = false;
            for (int i = 0; i < faculty.getNumCoursesTaught(); i++) {
                if (faculty.getCourseTaught(i).equals(existingCourse)) {
                    alreadyTeaching = true;
                    break;
                }
            }

            // Add course
            if(!alreadyTeaching) {
                faculty.addCourseTaught(existingCourse);
                addedCourses.add(existingCourse);
            }
        }
        return addedCourses;
    }
}