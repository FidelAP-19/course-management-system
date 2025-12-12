package com.TonyPerez.coursemanagement.repository;

import com.TonyPerez.coursemanagement.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    // No custom methods needed for now
}