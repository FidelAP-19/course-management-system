package com.TonyPerez.coursemanagement.service;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.domain.Student;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import com.TonyPerez.coursemanagement.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepo, CourseRepository courseRepo) {
        this.studentRepository = studentRepo;
        this.courseRepository = courseRepo;
    }

    public List<Course> addCoursesToStudent(int studentId, List<Course> courses) {
        ArrayList<Course> coursesAdded = new ArrayList<>();


        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // Validate and add courses
        for(Course course : courses) {

            Course existingCourse = courseRepository
                    .findByCourseDeptAndCourseNum(course.getCourseDept(), course.getCourseNum());

            if(existingCourse == null) {
                throw new IllegalArgumentException("Course " + course.getCourseName() + " not found");
            }

            // Check for duplicate
            boolean alreadyEnrolled = false;
            for(int i = 0; i < student.getNumCoursesTaken(); i++) {
                if(student.getCourseTaken(i).equals(existingCourse)) {
                    alreadyEnrolled = true;
                    break;
                }
            }

            // Add course
            if(!alreadyEnrolled) {
                student.addCourseTaken(existingCourse);
                coursesAdded.add(existingCourse);
            }
        }
        return coursesAdded;
    }
}