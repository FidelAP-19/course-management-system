package service;

import repository.CourseRepository;
import repository.FacultyRepository;
import java.util.ArrayList;
import java.util.List;
import domain.Course;
import domain.Faculty;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FacultyServiceTest {
    @Test
    public void testAddCoursesToFaculty_Success_ReturnsAddedCourses(){
        //Arrange- Prepare/Create data for testing
        CourseRepository courseRepo = new CourseRepository();
        FacultyRepository facultyRepo = new FacultyRepository();
        FacultyService service = new FacultyService(facultyRepo, courseRepo);

        Course course = new Course(true, 777, "CMP", 4);
        courseRepo.add(course);

        Faculty faculty = new Faculty("Dr. Smith", 1980, "CMP", true);
        facultyRepo.add(faculty);

        List<Course> courses = new ArrayList<>();
        courses.add(course);
        //Act
        List <Course> addedCourses = service.addCoursesToFaculty(faculty.getEmployeeID(), courses);
        //Assert
        assertNotNull(addedCourses, "Returned List should not be null");
        assertEquals(1, courses.size(), "Should have 1 added course");
        assertEquals(course, addedCourses.get(0), "Should return the course added");
        assertEquals(1, faculty.getNumCoursesTaught(), "Faculty should now teach 1 course");

    }
    @Test
    public void testAddCoursesToFaculty_FacultyNotFound_ThrowsException() {
        // Arrange
        CourseRepository courseRepo = new CourseRepository();
        FacultyRepository facultyRepo = new FacultyRepository();
        FacultyService service = new FacultyService(facultyRepo, courseRepo);

        Course course = new Course(true, 777, "CMP", 4);
        courseRepo.add(course);
        // Note: We DON'T add any faculty!

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        // Act & Assert - Verify exception is thrown
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.addCoursesToFaculty(999, courses)  // Invalid faculty ID
        );

        assertEquals("Faculty not found", exception.getMessage());
    }

    @Test
    public void testAddCoursesToFaculty_CourseNotFound_ThrowsException() {
        CourseRepository courseRepo = new CourseRepository();
        FacultyRepository facultyRepo = new FacultyRepository();
        FacultyService service = new FacultyService(facultyRepo, courseRepo);

        Faculty faculty = new Faculty("Dr. Smith", 1980, "CMP", true);
        facultyRepo.add(faculty);
        // Note: We DON'T add the course to the repository!

        List<Course> courses = new ArrayList<>();
        Course nonExistentCourse = new Course(true, 999, "XYZ", 4);
        courses.add(nonExistentCourse);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.addCoursesToFaculty(faculty.getEmployeeID(), courses)
        );

        assertTrue(exception.getMessage().contains("Course"));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    public void testAddCoursesToFaculty_DuplicateCourse_SkipsSilently() {
        CourseRepository courseRepo = new CourseRepository();
        FacultyRepository facultyRepo = new FacultyRepository();
        FacultyService service = new FacultyService(facultyRepo, courseRepo);

        Course course = new Course(true, 777, "CMP", 4);
        courseRepo.add(course);

        Faculty faculty = new Faculty("Dr. Smith", 1980, "CMP", true);
        faculty.addCourseTaught(course);  // Faculty ALREADY teaches this course!
        facultyRepo.add(faculty);

        List<Course> courses = new ArrayList<>();
        courses.add(course);


        List<Course> addedCourses = service.addCoursesToFaculty(faculty.getEmployeeID(), courses);

        // Assert - Should return empty list (nothing added)
        assertEquals(0, addedCourses.size(), "Should not add duplicate course");
        assertEquals(1, faculty.getNumCoursesTaught(), "Faculty should still teach only 1 course");
    }

    @Test
    public void testAddCoursesToFaculty_MultipleCoursesWithDuplicates_ReturnsOnlyNewOnes() {
        // Arrange
        CourseRepository courseRepo = new CourseRepository();
        FacultyRepository facultyRepo = new FacultyRepository();
        FacultyService service = new FacultyService(facultyRepo, courseRepo);

        Course course1 = new Course(true, 777, "CMP", 4);
        Course course2 = new Course(true, 711, "CMP", 4);
        Course course3 = new Course(false, 168, "CMP", 4);
        courseRepo.add(course1);
        courseRepo.add(course2);
        courseRepo.add(course3);

        Faculty faculty = new Faculty("Dr. Smith", 1980, "CMP", true);
        faculty.addCourseTaught(course2);  // Already teaches course2
        facultyRepo.add(faculty);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);  // New
        courses.add(course2);  // Duplicate!
        courses.add(course3);  // New

        // Act
        List<Course> addedCourses = service.addCoursesToFaculty(faculty.getEmployeeID(), courses);

        // Assert
        assertEquals(2, addedCourses.size(), "Should add 2 new courses (skip 1 duplicate)");
        assertTrue(addedCourses.contains(course1), "Should include course1");
        assertFalse(addedCourses.contains(course2), "Should NOT include duplicate course2");
        assertTrue(addedCourses.contains(course3), "Should include course3");
        assertEquals(3, faculty.getNumCoursesTaught(), "Faculty should now teach 3 courses total");
    }

}