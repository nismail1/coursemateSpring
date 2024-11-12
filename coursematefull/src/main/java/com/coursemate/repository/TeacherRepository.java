package com.coursemate.repository;

import com.coursemate.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Add custom query methods if needed
}
