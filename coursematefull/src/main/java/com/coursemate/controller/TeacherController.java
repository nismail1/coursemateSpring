package com.coursemate.controller;

import com.coursemate.model.Teacher;
import com.coursemate.model.Grade;
import com.coursemate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Optional<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

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
