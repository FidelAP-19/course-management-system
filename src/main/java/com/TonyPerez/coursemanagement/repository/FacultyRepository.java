package com.TonyPerez.coursemanagement.repository;

import com.TonyPerez.coursemanagement.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for Faculty entity.
 *
 * Spring automatically provides:
 * - Optional<Faculty> findById(Integer id)
 * - List<Faculty> findAll()
 * - Faculty save(Faculty faculty)
 * - void deleteById(Integer id)
 * - long count()
 * - boolean existsById(Integer id)
 */
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    List <Faculty> findByDeptName(String deptName);
    List <Faculty> findByIsTenured(Boolean isTenured);
    List<Faculty> findByDeptNameAndIsTenured(String deptName, Boolean isTenured);
}