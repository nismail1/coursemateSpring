package com.coursemate.service;

import com.coursemate.model.SystemUser;
import com.coursemate.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor-based injection of UserRepository
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("CustomUserDetailsService initialized");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database based on the username (or email)
        System.out.println("Username being searched: " + username);  // Print or log the username
        SystemUser systemUser = userRepository.findByEmail(username);
        System.out.println("Username being searched: " + username+systemUser);
        // If the user is not found, throw an exception
        if (systemUser == null) {
            System.out.println("Username being searched: " + username+"why did it go in here?");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
         // Get the role without the "ROLE_" prefix (if it exists)
        String role = systemUser.getRole();
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);  // Remove "ROLE_" prefix
        }
        // Create and return a UserDetails object with the user's details and roles
        return User.builder()
                .username(systemUser.getEmail())  // Use the email as the username
                .password(systemUser.getPassword())  // The encoded password
                .roles(role)  // The role(s) of the user (single role in this case)
                .build();
    }
}
