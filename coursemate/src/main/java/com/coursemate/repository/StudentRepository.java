package com.coursemate.repository;

import com.coursemate.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Add custom query methods if needed
}
