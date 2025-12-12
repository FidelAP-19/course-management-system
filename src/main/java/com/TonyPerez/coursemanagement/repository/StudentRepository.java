package com.TonyPerez.coursemanagement.repository;

import com.TonyPerez.coursemanagement.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StudentRepository extends JpaRepository<Student, Integer> {


}
