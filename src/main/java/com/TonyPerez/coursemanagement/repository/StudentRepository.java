package com.TonyPerez.coursemanagement.repository;

import com.TonyPerez.coursemanagement.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List <Student> findByMajor(String major);

    List <Student> findByBirthYear(Integer birthYear);

    List <Student> findByMajorAndBirthYear(String major, Integer birthYear);
}
