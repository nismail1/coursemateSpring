package com.coursemate.service;
import com.coursemate.model.Grade;
import com.coursemate.model.Teacher;
import com.coursemate.repository.TeacherRepository;
import com.coursemate.repository.GradeRepository;

// import java.io.OptionalDataException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TeacherServiceImpl {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GradeRepository gradeRepository;

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }


    public Teacher updateTeacher(Long teacherId, Teacher teacherDetails) {
        Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
        if (teacherOpt.isPresent()) {
            Teacher teacher = teacherOpt.get();
            teacher.setFirstName(teacherDetails.getFirstName());
            teacher.setLastName(teacherDetails.getLastName());
            teacher.setCourses(teacherDetails.getCourses()); // Updating courses if necessary
            return teacherRepository.save(teacher);
        }
        throw new RuntimeException("Teacher not found");
    }


    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

   
    public Optional<Teacher> getTeacherById(Long teacherId) {
        // return teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
        return teacherRepository.findById(teacherId);
    }
    // New method for grading students
    public Grade assignGradeToStudent(Long studentId, Long courseId, String gradeValue) {
        Grade grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (grade == null) {
            grade = new Grade();
            // Set up student and course (fetch from repositories if needed)
        }
        grade.setGrade(gradeValue);
        return gradeRepository.save(grade);
    }
}
