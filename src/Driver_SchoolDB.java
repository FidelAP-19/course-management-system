

import java.util.*;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.IOException;

public class Driver_SchoolDB {

        public static void main(String[] args) {
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<Faculty> facultyList = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<GeneralStaff> staffList = new ArrayList<>();
        FacultyRepository facultyRepo = new FacultyRepository();
        CourseRepository courseRepo = new CourseRepository();

        FileInputStream fbs = null;
        Scanner inFS = null;
        String currLine;
        String fileName = "SchoolDB_Updated.txt";

        try {
            fbs = new FileInputStream("SchoolDB_Initial.txt");
            inFS = new Scanner(fbs);

            while (inFS.hasNextLine()) {
                currLine = inFS.nextLine();
                System.out.println(currLine);
            }
            System.out.println();
            inFS.close();

            fbs = new FileInputStream("SchoolDB_Initial.txt");
            inFS = new Scanner(fbs);

            while (inFS.hasNextLine()){
                String line = inFS.nextLine();
                String result = line.replaceAll(":\\s*", ",");
                result = result.replaceAll("\\s*,\\s*", ",");
                result = result.replaceAll("^,+|,+$", "");


                String [] parts = result.split(",");
                if(parts[0].equals("Course")){
                    Course c;
                    c = new Course(Boolean.parseBoolean(parts[1]),Integer.parseInt(parts[2]),parts[3],Integer.parseInt(parts[4]));
                    courseList.add(c);
                }
                if(parts[0].equals("Faculty")){
                    Faculty f;
                    if(parts.length == 1){
                        f = new Faculty();
                        facultyList.add(f);
                    }
                    else if(parts.length == 2){
                        f = new Faculty(Boolean.parseBoolean(parts[1]));
                        facultyList.add(f);
                    }
                    else if(parts.length == 3){
                        f = new Faculty(parts[1],Boolean.parseBoolean(parts[2]));
                        facultyList.add(f);
                    }
                    else if(parts.length == 5){
                        f = new Faculty(parts[1],Integer.parseInt(parts[2]),parts[3],Boolean.parseBoolean(parts[4]));
                        facultyList.add(f);
                    }

                }
                if(parts[0].equals("Student")){
                    if(parts.length == 1){
                        studentList.add(new Student());
                    }
                    else if(parts.length == 2){
                        studentList.add(new Student(Boolean.parseBoolean(parts[1])));
                    }
                    else if(parts.length == 3){
                        studentList.add(new Student(parts[1],Boolean.parseBoolean(parts[2])));
                    }
                    else if(parts.length == 5){
                        studentList.add(new Student(parts[1],Integer.parseInt(parts[2]),parts[3],Boolean.parseBoolean(parts[4])));
                    }
                }
                if(parts[0].equals("GeneralStaff")){
                    if(parts.length == 1){
                        staffList.add(new GeneralStaff());
                    }
                    else if(parts.length == 2){
                        staffList.add(new GeneralStaff(parts[1]));
                    }
                    else if(parts.length == 3){
                        staffList.add(new GeneralStaff(parts[1],parts[2]));
                    }
                    else if(parts.length == 5){
                        staffList.add(new GeneralStaff(parts[1],Integer.parseInt(parts[2]),parts[3],parts[4]));
                    }
                }

            }
        }
        catch (FileNotFoundException e){
            System.out.println("Cannot find file");
        }
        catch (IOException e){
            System.out.println("Error reading file");
        }

        for(Faculty f : facultyList){
            facultyRepo.add(f);
        }
        for(Course c : courseList){
            courseRepo.add(c);
        }

        FacultyService facultyService = new FacultyService(facultyRepo, courseRepo);

            try {
                List<Course> coursesToAdd = new ArrayList<>();
                coursesToAdd.add(courseList.get(0));  // Add first course from list
                facultyService.addCoursesToFaculty(1, coursesToAdd);  // Add to faculty ID 1
                System.out.println("SUCCESS: Service layer works!");
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            Scanner scnr = new Scanner(System.in);
            FacultyMenuController facultyController = new FacultyMenuController(
                    scnr,
                    facultyRepo,
                    courseRepo,
                    facultyService
            );


        Boolean running = true;

        while(running){
            try {
                displayMainMenu();
                int choice = scnr.nextInt();
                scnr.nextLine();

                switch (choice) {
                    case 1:
                        createThreeCourses(scnr, courseList);
                        break;
                    case 2:
                        createThreeFaculty(scnr, facultyList);
                        break;
                    case 3:
                        createThreeStaff(scnr, staffList);
                        break;
                    case 4:
                        createThreeStudents(scnr, studentList);
                        break;
                    case 5:
                        addTwoCoursesToFaculty(scnr, courseList, facultyList);
                        break;
                    case 6:
                        addTwoCoursesToStudent(scnr, courseList, studentList);
                        break;
                    case 7:
                        addCourseArrayToFaculty(scnr, courseList, facultyList);
                        break;
                    case 8:
                        addCourseArrayToStudent(scnr, courseList, studentList);
                        break;
                    case 9:
                        getCourseFromFaculty(scnr, facultyList);
                        break;
                    case 10:
                        getCourseFromStudent(scnr, studentList);
                        break;
                    case 11:
                        queryFacultyForCourse(scnr, facultyList, courseList);
                        break;
                    case 12:
                        findFacultyMostLeastCourses(facultyList);
                        break;
                    case 13:
                        findMinMaxCourse(courseList);
                        break;
                    case 14:
                        findStudentMostLeastCredits(studentList);
                        break;
                    case 15:
                        printAllData(courseList, facultyList, studentList, staffList);
                        break;
                    case 16:
                        facultyController.addCoursesToFaculty();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }
            writeToFile(fileName,courseList,facultyList,studentList,staffList);

            printAllData(courseList,facultyList,studentList,staffList);

    }
    private static void displayMainMenu() {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Create 3 New Courses");
            System.out.println("2. Create 3 New Faculty");
            System.out.println("3. Create 3 New General Staff");
            System.out.println("4. Create 3 New Students");
            System.out.println("5. Add 2 Courses to Faculty");
            System.out.println("6. Add 2 Courses to Student");
            System.out.println("7. Add Array of 2 Courses to Faculty");
            System.out.println("8. Add Array of 2 Courses to Student");
            System.out.println("9. Get Course from Faculty by Index");
            System.out.println("10. Get Course from Student by Index");
            System.out.println("11. Query Faculty for Course");
            System.out.println("12. Faculty with Most/Least Courses");
            System.out.println("13. Find Min/Max Course");
            System.out.println("14. Student with Most/Least Credits");
            System.out.println("15. Display All Data");
            System.out.println("16. Add Courses to Faculty (New Controller)");
            System.out.println("0. Exit and Save");
            System.out.print("\nEnter choice: ");
    }
    private static void createThreeCourses(Scanner scnr, ArrayList<Course> courseList){
        System.out.println("\n--- Enter 3 New Courses ---");
        for(int i = 0; i < 3; ++i){
            System.out.println("Course " + (i + 1) + ":");

            System.out.println("Are you a Graduate(true/false)?");
            boolean isGrad = scnr.nextBoolean();
            scnr.nextLine();

            System.out.println("Enter your Course Number:");
            int courseNum = scnr.nextInt();
            scnr.nextLine();

            System.out.println("Enter your Department ex.(CMP):");
            String dept = scnr.nextLine();

            System.out.println("Enter the number of credits the course is worth");
            int credits = scnr.nextInt();
            scnr.nextLine();

            Course c = new Course(isGrad, courseNum, dept, credits);
            courseList.add(c);
            System.out.println("Created: " + c.getCourseName());

        }
        System.out.println("All 3 courses created successfully!");
    }

    private static void createThreeFaculty(Scanner scnr, ArrayList<Faculty> facultyList){
        System.out.println("\n--- Enter 3 New Faculty Members ---");
        for(int i = 0; i < 3; ++i){
            System.out.println("Faculty " + (i + 1) + ":");

            System.out.println("Enter your Full Name?");
            String fullName = scnr.nextLine();

            System.out.println("Enter your Birth Year:");
            int birthYear = scnr.nextInt();
            scnr.nextLine();

            System.out.println("Enter your Department:");
            String dept = scnr.nextLine();

            System.out.println("Are you tenured(true/false)?");
            boolean tenured = scnr.nextBoolean();
            scnr.nextLine();

            Faculty f = new Faculty(fullName, birthYear, dept, tenured);
            facultyList.add(f);
            System.out.println("Created: " + f.getName());

        }
        System.out.println("All 3 faculty members created successfully!");
    }
    private static void createThreeStaff(Scanner scnr, ArrayList<GeneralStaff> staffList){
        System.out.println("\n--- Enter 3 New Staff Members ---");
        for(int i = 0; i < 3; ++i){
            System.out.println("Staff " + (i + 1) + ":");

            System.out.println("Enter your Full Name?");
            String fullName = scnr.nextLine();

            System.out.println("Enter your Birth Year:");
            int birthYear = scnr.nextInt();
            scnr.nextLine();

            System.out.println("Enter your Department:");
            String dept = scnr.nextLine();

            System.out.println("What is your duty?");
            String duty = scnr.nextLine();

            GeneralStaff sf = new GeneralStaff(fullName, birthYear, dept, duty);
            staffList.add(sf);
            System.out.println("Created: " + sf.getName());

        }
        System.out.println("All 3 staff members created successfully!");
    }

    private static void createThreeStudents(Scanner scnr, ArrayList<Student> studentList){
        System.out.println("\n--- Enter 3 New Students ---");
        for(int i = 0; i < 3; ++i){
            System.out.println("Student " + (i + 1) + ":");

            System.out.println("Enter your Full Name?");
            String fullName = scnr.nextLine();

            System.out.println("Enter your Birth Year:");
            int birthYear = scnr.nextInt();
            scnr.nextLine();

            System.out.println("Enter your Major:");
            String major = scnr.nextLine();

            System.out.println("Are you a Graduate(true/false)?");
            boolean grad = scnr.nextBoolean();
            scnr.nextLine();

            Student s = new Student(fullName, birthYear, major, grad);
            studentList.add(s);
            System.out.println("Created: " + s.getName());

        }
        System.out.println("All 3 students created successfully!");
    }
    private static void addTwoCoursesToFaculty(Scanner scnr, ArrayList<Course> coursesList, ArrayList<Faculty> facultyList) {
        int facultyIndex = 0;
        int facultyArrayIndex = 0;
        Faculty member = null;
        boolean validInput = false;

        if(coursesList.isEmpty()) {
            System.out.println("Error: Courses List is empty!");
            return;
        }
        if(facultyList.isEmpty()) {
            System.out.println("Error: Faculty List is empty");
            return;
        }

        for(Faculty faculty: facultyList) {
            System.out.println(facultyIndex + " " + faculty);
            facultyIndex++;
        }

        while(!validInput) {
            try {
                System.out.println("Select a Faculty Member based on index");
                facultyArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if(facultyArrayIndex < 0 || facultyArrayIndex >= facultyList.size()){
                    int size = facultyList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + size);
                    continue;
                }
                member = facultyList.get(facultyArrayIndex);
                System.out.println("You selected Faculty member: " + member.getName());
                validInput = true;
            }
            catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

            for(int i = 0; i < 2;++i){
                boolean valid = false;
                int courseIndex = 0;

                for(Course courses: coursesList) {
                    System.out.println(courseIndex + " " + courses);
                    courseIndex++;
                }
                while (!valid) {
                    try {
                        System.out.println("Select the Course based on index");
                        int courseArrayIndex = scnr.nextInt();
                        scnr.nextLine();

                        if(courseArrayIndex < 0 || courseArrayIndex >= coursesList.size()){
                            int size = coursesList.size() - 1;
                            System.out.println("Error: Enter a number between 0 and " + size);
                            continue;
                        }
                        Course c = coursesList.get(courseArrayIndex);
                        System.out.println("You selected Course: " + c.getCourseName());
                        member.addCourseTaught(c);
                        System.out.println("Successfully added!");
                        valid = true;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Error: Invalid input. Please enter a number.");
                        scnr.nextLine();
                    }
                }
            }
        System.out.println("Successfully added both Courses to Faculty Member!");

    }
    private static void addTwoCoursesToStudent(Scanner scnr, ArrayList<Course> coursesList, ArrayList<Student> studentsList){
        int studentIndex = 0;
        int studentArrayIndex = 0;
        Student student = null;
        boolean validInput = false;

        if(coursesList.isEmpty()) {
            System.out.println("Error: Courses List is empty!");
            return;
        }
        if(studentsList.isEmpty()) {
            System.out.println("Error: Student List is empty");
            return;
        }

        for(Student students: studentsList) {
            System.out.println(studentIndex + " " + students);
            studentIndex++;
        }

        while(!validInput) {
            try {
                System.out.println("Select a Student based on index");
                studentArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if(studentArrayIndex < 0 || studentArrayIndex >= studentsList.size()){
                    int size = studentsList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + size);
                    continue;
                }
                student = studentsList.get(studentArrayIndex);
                System.out.println("You selected Student: " + student.getName());
                validInput = true;
            }
            catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

        for(int i = 0; i < 2;++i){
            boolean valid = false;
            int courseIndex = 0;

            for(Course courses: coursesList) {
                System.out.println(courseIndex + " " + courses);
                courseIndex++;
            }
            while (!valid) {
                try {
                    System.out.println("Select the Course based on index");
                    int courseArrayIndex = scnr.nextInt();
                    scnr.nextLine();

                    if(courseArrayIndex < 0 || courseArrayIndex >= coursesList.size()){
                        int size = coursesList.size() - 1;
                        System.out.println("Error: Enter a number between 0 and " + size);
                        continue;
                    }
                    Course c = coursesList.get(courseArrayIndex);
                    System.out.println("You selected Course: " + c.getCourseName());
                    student.addCourseTaken(c);
                    System.out.println("Successfully added!");
                    valid = true;
                }
                catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    scnr.nextLine();
                }
            }
        }
        System.out.println("Successfully added both Courses to Student!");

    }
    private static void addCourseArrayToFaculty(Scanner scnr, ArrayList<Course>coursesList, ArrayList <Faculty> facultyList){
        int facultyIndex = 0;
        int facultyArrayIndex = 0;
        Faculty member = null;
        boolean validInput = false;
        Course[] courseArray = new Course[2];

        if(coursesList.isEmpty()){
            System.out.println("Error: Courses List is empty!");
            return;
        }
        if(facultyList.isEmpty()) {
            System.out.println("Error: Faculty List is empty");
            return;
        }

        for(Faculty faculty: facultyList) {
            System.out.println(facultyIndex + " " + faculty);
            facultyIndex++;
        }

        while(!validInput) {
            try {
                System.out.println("Select a Faculty Member based on index");
                facultyArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if(facultyArrayIndex < 0 || facultyArrayIndex >= facultyList.size()){
                    int size = facultyList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + size);
                    continue;
                }
                member = facultyList.get(facultyArrayIndex);
                System.out.println("You selected Faculty member: " + member.getName());
                validInput = true;
            }
            catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

        for(int i = 0; i < 2;++i){
            boolean valid = false;
            int courseIndex = 0;

            for(Course courses: coursesList) {
                System.out.println(courseIndex + " " + courses);
                courseIndex++;
            }
            while (!valid) {
                try {
                    System.out.println("Select the Course based on index");
                    int courseArrayIndex = scnr.nextInt();
                    scnr.nextLine();

                    if(courseArrayIndex < 0 || courseArrayIndex >= coursesList.size()){
                        int size = coursesList.size() - 1;
                        System.out.println("Error: Enter a number between 0 and " + size);
                        continue;
                    }
                    Course c = coursesList.get(courseArrayIndex);
                    System.out.println("You selected Course: " + c.getCourseName());
                    courseArray[i] = c;
                    System.out.println("Successfully added!");
                    valid = true;
                }
                catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    scnr.nextLine();
                }
            }
        }
        member.addCoursesTaught(courseArray);
        System.out.println("Successfully added Courses to Faculty Member!");

    }
    private static void addCourseArrayToStudent(Scanner scnr, ArrayList<Course>coursesList, ArrayList <Student> studentsList){
        int studentIndex = 0;
        int studentArrayIndex = 0;
        Student student = null;
        boolean validInput = false;
        Course [] coursesArray = new Course[2];

        if(coursesList.isEmpty()) {
            System.out.println("Error: Courses List is empty!");
            return;
        }
        if(studentsList.isEmpty()) {
            System.out.println("Error: Student List is empty");
            return;
        }

        for(Student students: studentsList) {
            System.out.println(studentIndex + " " + students);
            studentIndex++;
        }

        while(!validInput) {
            try {
                System.out.println("Select a Student based on index");
                studentArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if(studentArrayIndex < 0 || studentArrayIndex >= studentsList.size()){
                    int size = studentsList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + size);
                    continue;
                }
                student = studentsList.get(studentArrayIndex);
                System.out.println("You selected Student: " + student.getName());
                validInput = true;
            }
            catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

        for(int i = 0; i < 2;++i){
            boolean valid = false;
            int courseIndex = 0;

            for(Course courses: coursesList) {
                System.out.println(courseIndex + " " + courses);
                courseIndex++;
            }
            while (!valid) {
                try {
                    System.out.println("Select the Course based on index");
                    int courseArrayIndex = scnr.nextInt();
                    scnr.nextLine();

                    if(courseArrayIndex < 0 || courseArrayIndex >= coursesList.size()){
                        int size = coursesList.size() - 1;
                        System.out.println("Error: Enter a number between 0 and " + size);
                        continue;
                    }
                    Course c = coursesList.get(courseArrayIndex);
                    System.out.println("You selected Course: " + c.getCourseName());
                    coursesArray[i] = c;
                    System.out.println("Successfully added!");
                    valid = true;
                }
                catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    scnr.nextLine();
                }
            }
        }
        student.addCoursesTaken(coursesArray);
        System.out.println("Successfully added Courses to Student!");

    }

    private static void getCourseFromFaculty(Scanner scnr, ArrayList<Faculty> facultyList) {
        int facultyArrayIndex = 0;
        int courseIndex = 0;
        Faculty member = null;
        boolean validInput = false;
        boolean validIndex = false;

        if (facultyList.isEmpty()) {
            System.out.println("Error: Faculty List is empty");
            return;
        }

        int facultyIndex = 0;
        for (Faculty faculty : facultyList) {
            System.out.println(facultyIndex + " " + faculty);
            facultyIndex++;
        }

        while (!validInput) {
            try {
                System.out.println("Select a Faculty Member based on index");
                facultyArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if (facultyArrayIndex < 0 || facultyArrayIndex >= facultyList.size()) {
                    int listSize = facultyList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + listSize);
                    continue;
                }
                member = facultyList.get(facultyArrayIndex);
                System.out.println("You selected Faculty member: " + member.getName());
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

        int numCoursesTaught = member.getNumCoursesTaught();

        if(numCoursesTaught <= 0) {
            System.out.println("No courses under this Faculty member");
            return;
        }

        for (int i = 0; i < numCoursesTaught; ++i) {
            System.out.println(i + ": " + member.getCourseTaughtAsString(i));
        }
        System.out.println("Select a course based on index");

        while (!validIndex) {
            try {
                courseIndex = scnr.nextInt();
                scnr.nextLine();
                Course c = member.getCourseTaught(courseIndex);
                if (c == null) {
                    System.out.println("Invalid index");
                    continue;
                }
                System.out.println(c);
                validIndex = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }
    }
    private static void getCourseFromStudent(Scanner scnr, ArrayList<Student> studentsList) {
        int studentIndex = 0;
        int studentArrayIndex = 0;
        int courseIndex = 0;
        Student studentObj = null;
        boolean validInput = false;
        boolean validIndex = false;

        if (studentsList.isEmpty()) {
            System.out.println("Error: Student List is empty");
            return;
        }

        for (Student students : studentsList) {
            System.out.println(studentIndex + " " + students);
            studentIndex++;
        }

        while (!validInput) {
            try {
                System.out.println("Select a Student based on index");
                studentArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if (studentArrayIndex < 0 || studentArrayIndex >= studentsList.size()) {
                    int listSize = studentsList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + listSize);
                    continue;
                }
                studentObj = studentsList.get(studentArrayIndex);
                System.out.println("You selected Student: " + studentObj.getName());
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

        int numCoursesTaken = studentObj.getNumCoursesTaken();

        if(numCoursesTaken <= 0) {
            System.out.println("No courses under this Student at this time");
            return;
        }

        for (int i = 0; i < numCoursesTaken; ++i) {
            System.out.println(i + ": " + studentObj.getCourseTakenAsString(i));
        }

        System.out.println("Select a course based on index");

        while (!validIndex) {
            try {
                courseIndex = scnr.nextInt();
                scnr.nextLine();
                Course c = studentObj.getCourseTaken(courseIndex);
                if (c == null) {
                    System.out.println("Invalid index");
                    continue;
                }
                System.out.println(c);
                validIndex = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }
    }
    private static void queryFacultyForCourse(Scanner scnr, ArrayList<Faculty> facultyList, ArrayList<Course> coursesList){
        int facultyArrayIndex = 0;
        Faculty member = null;
        boolean validInput = false;
        boolean validIndex = false;

        if (facultyList.isEmpty()) {
            System.out.println("Error: Faculty List is empty");
            return;
        }
        if (coursesList.isEmpty()) {
            System.out.println("Error: Courses List is empty");
            return;
        }

        int facultyIndex = 0;
        for (Faculty faculty : facultyList) {
            System.out.println(facultyIndex + " " + faculty);
            facultyIndex++;
        }

        while (!validInput) {
            try {
                System.out.println("Select a Faculty Member based on index");
                facultyArrayIndex = scnr.nextInt();
                scnr.nextLine();
                if (facultyArrayIndex < 0 || facultyArrayIndex >= facultyList.size()) {
                    int listSize = facultyList.size() - 1;
                    System.out.println("Error: Enter a number between 0 and " + listSize);
                    continue;
                }
                member = facultyList.get(facultyArrayIndex);
                System.out.println("You selected Faculty member: " + member.getName());
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }

        int courseIndex = 0;
        for(Course courses: coursesList){
            System.out.println(courseIndex + ": " + courses);
            courseIndex++;
        }

        System.out.println("Enter a course based on index:");
        Course c = null;
        while (!validIndex){
            try {
                courseIndex = scnr.nextInt();
                scnr.nextLine();
                if (courseIndex < 0 || courseIndex >= coursesList.size()) {
                    System.out.println("Invalid index");
                    continue;
                }
                c = coursesList.get(courseIndex);
                System.out.println("Selected Course:" + c.toString());
                validIndex = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scnr.nextLine();
            }
        }
        boolean teachCourse = false;
        for(int i = 0; i < member.getNumCoursesTaught(); ++i){
            Course facultyCourse = member.getCourseTaught(i);
            if (facultyCourse.equals(c)){
                teachCourse  = true;
                break;
            }
        }
        System.out.println();
        if (teachCourse) {
            System.out.println("Yes, " + member.getName() + " teaches " + c.getCourseName());
        } else {
            System.out.println("No, " + member.getName() + " does not teach " + c.getCourseName());
        }


    }
    private static void findFacultyMostLeastCourses(ArrayList<Faculty> facultyList){
            if(facultyList.isEmpty()){
                System.out.println("Error: Faculty List is empty");
                return;
            }

        Faculty mostFaculty = facultyList.get(0);
        Faculty leastFaculty = facultyList.get(0);

            for(Faculty faculty: facultyList){
                if(faculty.getNumCoursesTaught() > mostFaculty.getNumCoursesTaught()){
                    mostFaculty = faculty;
                }
                if(faculty.getNumCoursesTaught() < leastFaculty.getNumCoursesTaught()){
                    leastFaculty = faculty;
                }
            }
            System.out.println("Faculty member with most courses taught: " + (mostFaculty.getName()) + " - " + (mostFaculty.getNumCoursesTaught()));
            System.out.println("Faculty member with least courses taught: " + (leastFaculty.getName()) + " - " + (leastFaculty.getNumCoursesTaught()));

    }
    private static void findMinMaxCourse(ArrayList<Course> coursesList){
            if(coursesList.isEmpty()){
                System.out.println("Error: Course List is empty");
                return;
            }
            Course min = Collections.min(coursesList);
            Course max = Collections.max(coursesList);
            System.out.println("Minimum Course = " + min);
            System.out.println("Maximum Course = " + max);

    }
    private static void findStudentMostLeastCredits(ArrayList<Student> studentsList){
        if(studentsList.isEmpty()){
            System.out.println("Error; Student List is empty!");
            return;
        }
        Student leastStudentCredits = studentsList.get(0);
        Student mostStudentCredits = studentsList.get(0);
        int mostCredits = Integer.MIN_VALUE; ;
        int leastCredits = Integer.MAX_VALUE; ;

        for(Student student: studentsList){
            int totalCredits = 0;
           for(int i = 0;i < student.getNumCoursesTaken();++i){
               totalCredits += student.getCourseTaken(i).getNumCredits();
           }
           if(totalCredits > mostCredits){
               mostStudentCredits = student;
               mostCredits = totalCredits;
           }
           if(totalCredits < leastCredits){
               leastStudentCredits = student;
               leastCredits = totalCredits;
           }
        }

        System.out.println("The student with the least amount of total credits is " + leastStudentCredits.getName());
        System.out.println("The student with the most amount of total credits is " + mostStudentCredits.getName());

    }
    private static void writeToFile(String fileName, List<Course> courseList, List<Faculty> facultyList, List<Student> studentList, List<GeneralStaff> staffList){
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for(Course course: courseList){
                writer.println("Course: " + course.isGraduateCourse() + "," + course.getCourseNum() + "," + course.getCourseDept() + "," + course.getNumCredits());
            }
            for(Faculty faculty: facultyList){
                writer.println("Faculty: " + faculty.getName() + "," + faculty.getBirthYear() + "," + faculty.getDeptName() + "," + faculty.isTenured());
            }
            for(Student student: studentList){
                writer.println("Student: " + student.getName() + "," + student.getBirthYear() + "," + student.getMajor() + "," + student.isGraduate());
            }
            for(GeneralStaff staff: staffList){
                writer.println("GeneralStaff: " + staff.getName() + "," + staff.getBirthYear() + "," + staff.getDeptName() + "," + staff.getDuty());
            }
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found!");
        }

    }
    private static void printAllData(List<Course> courseList, List<Faculty> facultyList, List<Student> studentList, List<GeneralStaff> staffList){

        System.out.println("**************************************************************");
        System.out.println("SCHOOL DATABASE INFO:\n");
        System.out.println("************************************************");
        System.out.println("COURSES:");

        for(Course course:courseList){
            System.out.println(course.toString());
        }
        System.out.println("************************************************");
        System.out.println("************************************************");

        System.out.println("PERSONS:");
        System.out.println("************************************************");
        System.out.println("************************************************");
        System.out.println("EMPLOYEES:");
        System.out.println("************************************************");
        System.out.println("************************************************");
        System.out.println("GENERAL STAFF:");
        for(GeneralStaff staff:staffList){
            System.out.println(staff.toString());
        }
        System.out.println("************************************************");
        System.out.println("************************************************");
        System.out.println("FACULTY:");
        for(Faculty faculty:facultyList){
            System.out.println(faculty.toString());
        }
        System.out.println("************************************************");
        System.out.println("************************************************");
        System.out.println("STUDENTS:");
        for(Student student:studentList){
            System.out.println(student.toString());
        }
        System.out.println("************************************************");
        System.out.println("**************************************************************");
        System.out.println();


    }
}
