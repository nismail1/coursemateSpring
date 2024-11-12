package com.coursemate.repository;

import com.coursemate.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Add custom query methods if needed
}
