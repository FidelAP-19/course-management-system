package com.TonyPerez.coursemanagement.repository;

import com.TonyPerez.coursemanagement.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Course entity.
 *
 * JpaRepository<Course, Long> provides automatically:
 * - Optional<Course> findById(Long id)
 * - List<Course> findAll()
 * - Course save(Course course)
 * - void deleteById(Long id)
 * - long count()
 * - boolean existsById(Long id)
 *
 *
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find course by department and course number (natural key).
     * Spring parses the method name and generates SQL:
     * SELECT * FROM course WHERE course_dept = ? AND course_num = ?
     *
     * @param courseDept the department code (e.g., "CMP")
     * @param courseNum the course number (e.g., 168)
     * @return the course if found, null otherwise
     */
    Course findByCourseDeptAndCourseNum(String courseDept, int courseNum);
}