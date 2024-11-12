package com.coursemate.service;

import com.coursemate.model.Course;
import com.coursemate.model.Grade;
import com.coursemate.repository.CourseRepository;
import com.coursemate.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

   
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

  
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // New method for retrieving grades for a course
    public List<Grade> getGradesByCourse(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }
}
