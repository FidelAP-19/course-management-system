package controller;

import domain.Course;
import domain.Student;
import repository.CourseRepository;
import repository.StudentRepository;
import service.StudentService;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentMenuController {
    private Scanner scanner;
    private StudentService studentService;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public StudentMenuController(Scanner scanner, StudentService studentService, CourseRepository courseRepository, StudentRepository studentRepository){
        this.scanner = scanner;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }
    public void displayStudentList(){
        System.out.println("=== Student List ===");
        for(Student student: studentRepository.findAll()){
            System.out.printf("ID : %d | Name: %s | Courses: %s%n",
                    student.getStudentID(),
                    student.getName(),
                    student.getAllCoursesTakenAsString()
            );

        }


    }
    public void displayCourseList(){
        System.out.println("=== Course List ===");
        int index = 0;
        for(Course course : courseRepository.findAll()){
            System.out.printf("Course: %3s-%3d | Course Index: %d%n",
                    course.getCourseDept(),
                    course.getCourseNum(),
                    index);
            index++;
        }
    }
    public Course selectCourse(){
        displayCourseList();

        boolean validInput = false;
        int courseIndex = 0;
        Course c = null;
        while(!validInput){
            try{
                System.out.println("\nSelect a Course by entering a Course Index ");
                courseIndex = scanner.nextInt();
                scanner.nextLine();

                if(courseIndex < 0 || courseIndex >= courseRepository.count()){
                    System.out.println("Invalid index. Please enter a number between 0 and " + (courseRepository.count() - 1));
                    continue;
                }

                c = courseRepository.findAll().get(courseIndex);
                validInput = true;
            }catch (InputMismatchException e){
                System.out.println("Invalid Input! Enter a valid index number");
                scanner.nextLine();
            }catch (IndexOutOfBoundsException e){
                System.out.println("Invalid index! Please try again.");
                scanner.nextLine();
            }
        }
        return c;
    }
    public int selectStudentById(){
        displayStudentList();

        boolean validInput = false;
        int userStudentId = 0;
        while (!validInput){
            try{
                System.out.println("Enter the Student ID for a Student");
                userStudentId = scanner.nextInt();
                scanner.nextLine();
                if(studentRepository.findById(userStudentId) == null){
                    System.out.println("Error Student not Found. Enter a different Student ID");
                    continue;
                }
                validInput = true;
            }catch(InputMismatchException e){
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return userStudentId;
    }
    public void displayStudentDetails(int studentId) {
        Student student = studentRepository.findById(studentId);
        if (student != null) {
            System.out.printf("ID: %d | Name: %s | Courses: %s%n ",
                    student.getStudentID(),
                    student.getName(),
                    student.getAllCoursesTakenAsString()
            );
        }
    }
    public void addCoursesToStudent(){
        System.out.println("\n--- Add Courses to Student ---");
        //Checks
        if (courseRepository.count() == 0) {
            System.out.println("Error: No courses available. Please create courses first.");
            return;
        }
        if (studentRepository.count() == 0) {
            System.out.println("Error: No Students available. Please create a student first.");
            return;
        }
        //Select faculty member
        int studentId = selectStudentById();
        //Display selected Faculty Courses
        displayStudentDetails(studentId);
        //Choose 2 Courses
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Course course = selectCourse();
            courses.add(course);
        }
        //Call Service and handle exception
        try {
            List<Course> addedCourses = studentService.addCoursesToStudent(studentId, courses);
            int addedCourseSize = addedCourses.size();
            if(addedCourseSize == courses.size()) {
                System.out.println("SuccessFully added " + addedCourseSize + " Courses!");
                displayStudentDetails(studentId);
            } else if(addedCourseSize > 0){
                int difference = courses.size() - addedCourseSize;
                System.out.println("SuccessFully added " + addedCourseSize + " Courses! (" + difference + " duplicate skipped)");
                displayStudentDetails(studentId);
            }
            else{
                System.out.println("No courses added - all were duplicates");
            }

        }
        catch(IllegalArgumentException e){
            System.out.println("Error:" + e.getMessage());
            return;
        }

    }
}
