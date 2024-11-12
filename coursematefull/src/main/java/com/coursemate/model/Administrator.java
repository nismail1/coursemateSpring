package com.coursemate.model;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends SystemUser {
    
    // Constructors
    public Administrator() {
        super();
    }

    public Administrator(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "ADMIN");
    }
    
    // Additional methods for administrator-specific actions can be added here
}
