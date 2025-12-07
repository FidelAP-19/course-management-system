package service;

import domain.Course;
import domain.Student;
import repository.CourseRepository;
import repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepo, CourseRepository courseRepo){
        this.studentRepository = studentRepo;
        this.courseRepository = courseRepo;
    }
    public List<Course> addCoursesToStudent(int studentId, List<Course> courses){
        ArrayList<Course> coursesAdded = new ArrayList<>();
        // Find Student
        Student student = studentRepository.findById(studentId);
        if(student == null){
            throw new IllegalArgumentException("Student not found");
        }
        // Find and Validate Course
        for(Course course:courses){
            Course existingCourse = courseRepository.findByCourseDeptNum(course.getCourseNum(), course.getCourseDept());
            if(existingCourse == null){
                throw new IllegalArgumentException("Course " + course.getCourseName() + " not found");
            }
            //Check Course for duplicate
            boolean alreadyEnrolled = false;
            for(int i = 0; i < student.getNumCoursesTaken();i++){
                if(student.getCourseTaken(i).equals(existingCourse)){
                    alreadyEnrolled = true;
                    break;
                }
            }
            //Add Course
            if(!alreadyEnrolled){
                student.addCourseTaken(existingCourse);
                coursesAdded.add(existingCourse);
            }
        }
        return coursesAdded;
    }
}
