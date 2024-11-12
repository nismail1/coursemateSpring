package com.coursemate.service;

import com.coursemate.model.*;
import com.coursemate.model.SystemUser;

import com.coursemate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    // @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder;
    private PasswordEncoder passwordEncoder;
  
    // @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Generic registerUser method that handles different types of SystemUser
    public void registerUser(SystemUser user) {
        // // Encrypt the password before saving the user
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // // Save the user based on the type (Student, Teacher, etc.)
        // userRepository.save(user);
        System.out.println("Registering user: " + user.getEmail());
        if (user instanceof Student) {
            Student student = (Student) user;
            // Additional checks or logic here
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);  // Store the encoded password
            userRepository.save(student);  // Assuming you're using a JPA repository
            System.out.println("User registered successfully: " + student.getEmail());
    }
    if (user instanceof Teacher) {
        Teacher teacher = (Teacher) user;
        // Additional checks or logic here
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  // Store the encoded password
        userRepository.save(teacher);  // Assuming you're using a JPA repository
        System.out.println("User registered successfully: " + teacher.getEmail());
}
    
    if (user instanceof Administrator) {
        Administrator admin = (Administrator) user;
        // Additional checks or logic here
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);  // Store the encoded password
        userRepository.save(admin);  // Assuming you're using a JPA repository
        System.out.println("User registered successfully: " + admin.getEmail());
}}
    // Check if email exists in the database
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    // Authenticate the user based on email and password
    public SystemUser authenticate(String email, String password) {
        // Find the user by email
        SystemUser user = userRepository.findByEmail(email);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Return user if authentication is successful
            return user;
        }
        
        // Return null if authentication fails
        return null;
    }
}
