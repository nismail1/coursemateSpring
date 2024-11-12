package com.coursemate.controller;

import com.coursemate.model.Teacher;
import com.coursemate.model.Course;
import com.coursemate.model.Grade;
import com.coursemate.model.Student;
import com.coursemate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller

public class TeacherController {
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Optional<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }
    

    
    // public String assignGrades(@RequestParam Map<String, String> formParams, @RequestParam Long courseId) {
    //     // Iterate over the grades that were submitted in the form
    //     formParams.forEach((key, value) -> {
    //         if (key.startsWith("grades[")) {
    //             // Extract studentId from the parameter name
    //             String studentIdStr = key.substring("grades[".length(), key.length() - 1);
    //             Long studentId = Long.parseLong(studentIdStr);

    //             // Assign the grade to the student
    //             teacherService.assignGradeToStudent(studentId, courseId, value);
    //         }
    //     });

    //     // Redirect back to the teacher's course page or dashboard
    //     return "redirect:/dashboard/teacher/{id}";
    // }
    // Handle request to assign grades to multiple students
    @PostMapping("/dashboard/teacher/{teacherId}/assign-grade")
public String assignGradeToStudents(
        @PathVariable Long teacherId,
        @RequestParam Long courseId,
        @RequestParam Map<String, String> grades, // Change this to Map<String, String>
        Model model) {
    
    // Fetch teacher by ID
    Optional<Teacher> teacherOptional = teacherService.getTeacherById(teacherId);
    if (teacherOptional.isEmpty()) {
        model.addAttribute("error", "Teacher not found with id: " + teacherId);
        return "error-page";
    }
    Teacher teacher = teacherOptional.get();
    
    // Fetch course by ID
    Optional<Course> courseOptional = courseService.getCourseById(courseId);
    if (courseOptional.isEmpty()) {
        model.addAttribute("error", "Course not found with id: " + courseId);
        return "error-page";
    }
    Course course = courseOptional.get();
    
    // Iterate over the grades map, convert studentId from String to Long
    grades.forEach((studentIdStr, gradeValue) -> {
        try {
            Long studentId = Long.parseLong(studentIdStr); // Convert studentId to Long
            
            // Fetch student by ID
            Optional<Student> studentOptional = studentService.getStudentById(studentId);
            if (studentOptional.isEmpty()) {
                model.addAttribute("error", "Student not found with id: " + studentId);
            } else {
                Student student = studentOptional.get();
                // Ensure the student is enrolled in the course
                if (course.getStudents().contains(student)) {
                    teacherService.assignGradeToStudent(studentId, courseId, gradeValue);
                } else {
                    model.addAttribute("error", "Student " + student.getFirstName() + " " + student.getLastName() + " is not enrolled in this course.");
                }
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid student ID: " + studentIdStr);
        }
    });

    // Redirect to the teacher's dashboard after assigning grades
    return "redirect:/dashboard/teacher/" + teacher.getId();
}


    // Other methods related to teacher CRUD operations

    

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        Optional<Teacher> existingTeacher = teacherService.getTeacherById(id);
        if (existingTeacher.isPresent()) {
            updatedTeacher.setId(id);
            return teacherService.saveTeacher(updatedTeacher);
        }
        throw new RuntimeException("Teacher not found with ID: " + id);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    @PostMapping("/{teacherId}/grades")
    public Grade assignGradeToStudent(
            @PathVariable Long teacherId, 
            @RequestParam Long studentId, 
            @RequestParam Long courseId, 
            @RequestParam String gradeValue) {
        return teacherService.assignGradeToStudent(studentId, courseId, gradeValue);
    }
}
