package repository;

import domain.Course;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseRepositoryTest{

    @Test
    public void testFindByCourseDeptNum_WhenCourseExists_ReturnsTheCourse(){
        //Arrange
        CourseRepository repository = new CourseRepository();
        Course expectedCourse = new Course(true, 777, "CMP", 4);
        repository.add(expectedCourse);

        //Act
        Course actualCourse = repository.findByCourseDeptNum(777, "CMP");

        //Assert
        assertNotNull(actualCourse, "Course should be found");
        assertEquals(777, actualCourse.getCourseNum());
        assertEquals("CMP", actualCourse.getCourseDept());

    }
    @Test
    public void testFindByCourseDeptNum_WhenCourseDoesNotExist_ReturnsNull() {
        CourseRepository repository = new CourseRepository();

        Course result = repository.findByCourseDeptNum(999, "MAT");

        assertNull(result, "Course should not be found");
    }
//    @Test
//    public void testFailureExample() {
//        // This test will fail on purpose
//        CourseRepository repository = new CourseRepository();
//        Course course = new Course(true, 777, "CMP", 4);
//        repository.add(course);
//
//        Course result = repository.findByCourseDeptNum(777, "CMP");
//
//        // This assertion will FAIL because we're checking for the wrong department
//        assertEquals("MAT", result.getCourseDept(), "Expected department to be MAT");
//    }

}