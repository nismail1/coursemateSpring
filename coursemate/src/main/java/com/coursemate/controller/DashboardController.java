package com.coursemate.controller;

import com.coursemate.model.Administrator;
import com.coursemate.model.Student;
import com.coursemate.model.Teacher;
import com.coursemate.model.SystemUser;
import com.coursemate.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller

public class DashboardController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private AdministratorServiceImpl adminService;
    // Dashboard Redirection based on Role
    @GetMapping
    public String redirectToRoleDashboard(RedirectAttributes redirectAttributes) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Extract the User details from the authentication object

        // Based on the roles, redirect the user to their respective dashboard
        String username = user.getUsername(); // This is the username (email)
        String redirectUrl = "";

        // Example of fetching user details (ID) from the database if necessary
        // You should replace this with actual calls to your services to fetch the ID based on username
        Long userId = getUserIdFromUsername(username); // This is an example method to fetch the user ID

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            redirectUrl = "/dashboard/student/" + userId;
        } else if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
            redirectUrl = "/dashboard/teacher/" + userId;
        } else if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/dashboard/admin/" + userId;
        }

        // Redirect to the correct dashboard based on role
        return "redirect:" + redirectUrl;
    }
    private Long getUserIdFromUsername(String username) {
        // Fetch the student by username (email) and return the student ID
        // This is assuming the username is the email of the student, adjust the logic if needed
        Student student = studentService.getAllStudents().stream()
                .filter(s -> s.getEmail().equals(username))  // Compare username with student's email
                .findFirst()
                .orElse(null);

        // If student is not found, handle appropriately (throw exception, return default value, etc.)
        if (student != null) {
            return student.getId();  // Return the student ID
        } else {
            return null;  // Handle case where student is not found (you may want to redirect to an error page)
        }
    }
    // Dashboard for Student
    @GetMapping("/dashboard/student")
    public String getGenericStudentDashboard(){
        return "student-dashboard";
    }
    
    // Dashboard for Student
    @GetMapping("/dashboard/student/{id}")
    public String getStudentDashboard(@PathVariable Long id, Model model) {
        System.out.println("helllooo"+id);
        // Retrieve the student using the service method that returns Optional
        Optional<Student> studentOptional = studentService.getStudentById(id);
        
        // If student is not present, add an error message to the model
        if (studentOptional.isEmpty()) {
            model.addAttribute("error", "Student not found with id: " + id);
            return "error-page";  // Redirect or render an error template
        }

        // Add student data to the model
        Student student = studentOptional.get();
        model.addAttribute("studentId", student.getId());
        model.addAttribute("studentCourses", student.getEnrolledCourses());
        model.addAttribute("title", "Student Dashboard");

        return "student-dashboard";  // Name of the Thymeleaf template
    }

    // Dashboard for Teacher
    @GetMapping("/dashboard/teacher/{id}")
    public String getTeacherDashboard(@PathVariable Long id, Model model) {
        // Retrieve the teacher using the service method that returns Optional
        Optional<Teacher> teacherOptional = teacherService.getTeacherById(id);
        
        // If teacher is not present, add an error message to the model
        if (teacherOptional.isEmpty()) {
            model.addAttribute("error", "Teacher not found with id: " + id);
            return "error-page";  // Redirect or render an error template
        }

        // Add teacher data to the model
        Teacher teacher = teacherOptional.get();
        model.addAttribute("teacherId", teacher.getId());
        model.addAttribute("teacherCourses", teacher.getCourses());
        model.addAttribute("title", "Teacher Dashboard");

        return "teacher-dashboard";  // Name of the Thymeleaf template
    }

    // Dashboard for Administrator
    @GetMapping("/dashboard/admin/{id}")
    public String getAdminDashboard(@PathVariable Long id, Model model) {
        // Retrieve the administrator using the service method that returns Optional
        Optional<Administrator> adminOptional = adminService.getAdministratorById(id);

        // If admin is not present, add an error message to the model
        if (adminOptional.isEmpty()) {
            model.addAttribute("error", "Administrator not found with id: " + id);
            return "error-page";  // Redirect or render an error template
        }

        // Add admin data to the model
        SystemUser admin = adminOptional.get();
        model.addAttribute("adminId", admin.getId());
        model.addAttribute("title", "Admin Dashboard");

        return "admin-dashboard";  // Name of the Thymeleaf template
    }
}
