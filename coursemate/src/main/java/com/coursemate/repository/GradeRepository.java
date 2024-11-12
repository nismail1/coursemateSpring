package com.coursemate.repository;

import com.coursemate.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // Custom query methods

    // Find all grades for a specific student by their ID
    List<Grade> findByStudentId(Long studentId);

    // Find all grades for a specific course by its ID
    List<Grade> findByCourseId(Long courseId);

    // Find a grade for a specific student and course
    Grade findByStudentIdAndCourseId(Long studentId, Long courseId);
}
