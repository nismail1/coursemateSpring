package com.coursemate.controller;

import com.coursemate.model.Course;
import com.coursemate.model.Grade;
import com.coursemate.model.Teacher;
import com.coursemate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private StudentServiceImpl studentService;

    // @GetMapping
    // public List<Course> getAllCourses() {
    //     return courseService.getAllCourses();
    // }
    // View all courses (for both admin and students)
        @GetMapping
        public String getAllCourses(Model model) {
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);
            
            // Check if the current user is an admin or a student and show appropriate options
            if (isAdmin()) {
                return "manage-courses";  // Admin's course management page
            } else {
                return "view-courses";  // Student's course viewing page
            }
        }
        // Check if the current user is an admin
        private boolean isAdmin() {
                // Get the currently authenticated user from SecurityContext
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Check if the principal is an instance of the User class (Spring Security's default)
    if (principal instanceof User) {
        User user = (User) principal;  // Cast to Spring Security's User class
        // Check if the user has the "ROLE_ADMIN" authority
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
    return false;  // If the principal is not of type User, assume not an admin
        }
            // Show the form for creating a new course (admin-only)
        @GetMapping("/create")
        public String showCreateCourseForm(Model model) {
            // if (!isAdmin()) {
            //     return "redirect:/courses";  // Redirect if not an admin
            // }

            model.addAttribute("course", new Course());
            model.addAttribute("teachers", teacherService.getAllTeachers());
            return "create-course";  // Template for creating courses
        }
        @GetMapping("/manage-courses")
        public String manageCourses(Model model) {
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);
            return "manage-courses";  // Template for managing courses
        }

        // Handle course creation form submission (admin-only)
        @PostMapping("/create")
        public String createCourse(@ModelAttribute Course course, @RequestParam Long teacherId) {
            // if (!isAdmin()) {
            //     return "redirect:/courses";  // Redirect if not an admin
            // }

            Teacher teacher = teacherService.getTeacherById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
            course.setTeacher(teacher);
            courseService.saveCourse(course);
            return "redirect:/courses";  // Redirect to the course list after creation
        }

        // View details of a specific course (both admin and students)
        @GetMapping("/{id}")
        public String getCourseById(@PathVariable Long id, Model model) {
            Optional<Course> course = courseService.getCourseById(id);
            if (course.isPresent()) {
                model.addAttribute("course", course.get());
                model.addAttribute("students", studentService.getAllStudents());
                return "course-details";  // Template for viewing course details
            } else {
                return "redirect:/courses";  // Redirect if course not found
            }
        }
    // @GetMapping("/{id}")
    // public Optional<Course> getCourseById(@PathVariable Long id) {
    //     return courseService.getCourseById(id);
    // }

    // @PostMapping
    // public Course createCourse(@RequestBody Course course) {
    //     return courseService.saveCourse(course);
    // }

    // @PutMapping("/{id}")
    // public Course updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
    //     Optional<Course> existingCourse = courseService.getCourseById(id);
    //     if (existingCourse.isPresent()) {
    //         updatedCourse.setId(id);
    //         return courseService.saveCourse(updatedCourse);
    //     }
    //     throw new RuntimeException("Course not found with ID: " + id);
    // }

    // @DeleteMapping("/{id}")
    // public void deleteCourse(@PathVariable Long id) {
    //     courseService.deleteCourse(id);
    // }

    // @GetMapping("/{id}/grades")
    // public List<Grade> getGradesByCourse(@PathVariable Long id) {
    //     return courseService.getGradesByCourse(id);
    // }
}
