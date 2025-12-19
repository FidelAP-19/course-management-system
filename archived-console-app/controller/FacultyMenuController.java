package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Course;
import com.TonyPerez.coursemanagement.domain.Faculty;
import com.TonyPerez.coursemanagement.repository.CourseRepository;
import com.TonyPerez.coursemanagement.repository.FacultyRepository;
import com.TonyPerez.coursemanagement.service.FacultyService;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class FacultyMenuController {
    private Scanner scanner;
    private FacultyService facultyService;
    private CourseRepository courseRepository;
    private FacultyRepository facultyRepository;

    public FacultyMenuController(Scanner scanner, FacultyRepository facultyRepository, CourseRepository courseRepository, FacultyService facultyService){
        this.scanner = scanner;
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
        this.facultyService = facultyService;
    }
    private void displayFacultyList(){
        System.out.println("=== Faculty List ===");
        for(Faculty faculty : facultyRepository.findAll()){
            System.out.printf("ID : %d | Name: %s | Dept: %s | Courses: %s%n",
                    faculty.getEmployeeID(),
                    faculty.getName(),
                    faculty.getDeptName(),
                    faculty.getAllCoursesTaughtAsString()
            );

        }
    }
    private void displayCourseList(){
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
    private Course selectCourse(){
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
    private int selectFacultyById(){
        displayFacultyList();

        boolean validInput = false;
        int userFacultyId = 0;
        while (!validInput){
            try{
                System.out.println("Enter the Employee ID for a Faculty Member");
                userFacultyId = scanner.nextInt();
                scanner.nextLine();
                if(facultyRepository.findById(userFacultyId) == null){
                    System.out.println("Error Faculty Member not Found. Enter a different Employee ID");
                    continue;
                }
                validInput = true;
            }catch(InputMismatchException e){
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return userFacultyId;
    }
    private void displayFacultyDetails(int facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElse(null);
        if (faculty != null) {
            System.out.printf("ID: %d | Name: %s | Dept: %s | Courses: %s%n ",
                    faculty.getEmployeeID(),
                    faculty.getName(),
                    faculty.getDeptName(),
                    faculty.getAllCoursesTaughtAsString()
            );
        }
    }
    public void addCoursesToFaculty(){
        System.out.println("\n--- Add Courses to Faculty ---");
        //Checks
        if (courseRepository.count() == 0) {
            System.out.println("Error: No courses available. Please create courses first.");
            return;
        }
        if (facultyRepository.count() == 0) {
            System.out.println("Error: No faculty members available. Please create faculty first.");
            return;
        }
        //Select faculty member
        int facultyId = selectFacultyById();
        //Display selected Faculty Courses
        displayFacultyDetails(facultyId);
        //Choose 2 Courses
        ArrayList <Course> courses = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Course course = selectCourse();
            courses.add(course);
        }
        //Call Service and handle exception
        try {
            List<Course> addedCourses = facultyService.addCoursesToFaculty(facultyId, courses);
            int addedCourseSize = addedCourses.size();
            if(addedCourseSize == courses.size()) {
                System.out.println("SuccessFully added " + addedCourseSize + " Courses!");
                displayFacultyDetails(facultyId);
            } else if(addedCourseSize > 0){
                int difference = courses.size() - addedCourseSize;
                System.out.println("SuccessFully added " + addedCourseSize + " Courses! (" + difference + " duplicate skipped)");
                displayFacultyDetails(facultyId);
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
