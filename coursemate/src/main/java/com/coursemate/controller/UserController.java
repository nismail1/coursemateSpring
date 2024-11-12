package com.coursemate.controller;

import com.coursemate.model.Administrator;
import com.coursemate.model.Student;
import com.coursemate.model.SystemUser;
import com.coursemate.model.Teacher;
import com.coursemate.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class UserController {

    @Autowired
    private UserServiceImpl userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    // Redirect to the signup page when the root URL is accessed
    @RequestMapping("/")
    public String redirectToSignUp() {
        return "redirect:/signup/student";  // Redirect to the student signup page
    }
    // Show the student registration form
    @GetMapping("/signup/student")
    public String showStudentSignUpForm() {
        logger.info("Rendering student sign-up page");
        return "student-signup";  // Thymeleaf template for student sign-up
    }
    
    // Show the teacher registration form
    @GetMapping("/signup/teacher")
    public String showTeacherSignUpForm() {
        return "teacher-signup";  // Thymeleaf template for teacher sign-up
    }
    // Show the teacher registration form
    @GetMapping("/signup/administrator")
    public String showAdministratorSignUpForm() {
        return "administrator-signup";  // Thymeleaf template for teacher sign-up
    }
    @Autowired
private AuthenticationManager authenticationManager;
@Autowired
private PasswordEncoder passwordEncoder;
    // Handle student registration form submission
    @PostMapping("/signup/student")
    public String registerStudent(@RequestParam String firstName, 
                                  @RequestParam String lastName, 
                                  @RequestParam String email, 
                                  @RequestParam String password,
                                  @RequestParam String major,  // Example for student-specific field
                                  Model model) {
        logger.info("Rendering post");
        
        // Check if the email already exists in the database
        if (userService.emailExists(email)) {
            model.addAttribute("error", "Email already taken. Please choose another one.");
            return "student-signup";  // Show the student sign-up page with an error message
        }
            //Encode the password before saving
        // String encodedPassword = passwordEncoder.encode(password);
        Student student = new Student(firstName, lastName, email, password);
        try {
            userService.registerUser(student);  // Register the student
            
             // Save student to the database
            // userService.saveStudent(student);
            //Authenticate the user after registration
           authenticateUser(student);
        //    authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(email, password));
            System.out.println(student.getPassword()+"Name"+student.getLastName()+"LastName"+student.getId());
            return "redirect:/dashboard/student/" + student.getId();  // Redirect to login after successful registration
            //return "redirect:/dashboard/student/" + user.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Try again!");
            return "student-signup";  // Show the student sign-up page again in case of error
        }
    }
    // Helper method to authenticate the user
    private void authenticateUser(SystemUser user) {
       // Manually authenticate the user after registration
       System.out.println(user.getEmail()+ user.getPassword());
       UsernamePasswordAuthenticationToken authToken = 
       new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
    //     authenticationManager.authenticate(authToken);
    //     SecurityContextHolder.getContext().setAuthentication(authToken);
    //     // Log the authentication process for debugging
    //     System.out.println("User authenticated"+user.getEmail());
    //     logger.info("User authenticated: " + user.getEmail());
        try {
            authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.info("User authenticated: " + user.getEmail());
            System.out.println("User authenticated: " + user.getEmail());
        } catch (Exception e) {
            logger.error("Authentication failed for user: " + user.getEmail(), e);
        }
        
        
    }
    // Mapping for the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Thymeleaf template for the login page
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        // Validate user login (you can customize this logic as needed)
        SystemUser user = userService.authenticate(email, password);
        
        if (user != null) {
            // Based on the role, redirect to the appropriate dashboard
            if (user instanceof Student) {
                return "redirect:/dashboard/student/" + user.getId();
            } else if (user instanceof Teacher) {
                return "redirect:/dashboard/teacher/" + user.getId();
            } else if (user instanceof Administrator) {
                return "redirect:/dashboard/admin/" + user.getId();
            }
        }

        model.addAttribute("error", "Invalid credentials.");
        return "login";  // If authentication fails, return to the login page
    }
    // Handle student registration form submission
    @PostMapping("/signup/administrator")
    public String registerAdministrator(@RequestParam String firstName, 
                                  @RequestParam String lastName, 
                                  @RequestParam String email, 
                                  @RequestParam String password,
                                  Model model) {
        // Check if the email already exists in the database
        if (userService.emailExists(email)) {
            model.addAttribute("error", "Email already taken. Please choose another one.");
            return "administrator-signup";  // Show the student sign-up page with an error message
        }
        Administrator admin = new Administrator(firstName, lastName, email, password);
        try {
            userService.registerUser(admin);  // Register the student
            authenticateUser(admin);
            return "redirect:/dashboard/administrator/" + admin.getId();  // Redirect to login after successful registration
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Try again!");
            return "administrator-signup";  // Show the administrator sign-up page again in case of error
        }
         

        
    }

    // Handle teacher registration form submission
    @PostMapping("/signup/teacher")
    public String registerTeacher(@RequestParam String firstName, 
                                  @RequestParam String lastName, 
                                  @RequestParam String email, 
                                  @RequestParam String password,
                                  @RequestParam String department,  // Example for teacher-specific field
                                  Model model) {
        // Check if the email already exists in the database
        if (userService.emailExists(email)) {
            model.addAttribute("error", "Email already taken. Please choose another one.");
            return "teacher-signup";  // Show the student sign-up page with an error message
        }
        Teacher teacher = new Teacher(firstName, lastName, email, password);
        try {
            userService.registerUser(teacher);  // Register the teacher
            authenticateUser(teacher);
            return "redirect:/dashboard/teacher/" + teacher.getId();  // Redirect to login after successful registration
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Try again!");
            return "teacher-signup";  // Show the teacher sign-up page again in case of error
        }
    }
}
