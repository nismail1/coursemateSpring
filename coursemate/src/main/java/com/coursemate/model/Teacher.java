package com.coursemate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Teacher extends SystemUser {
    
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

   // Constructors
    public Teacher() {
        super();
    }

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "TEACHER");
    }

    // Getters and setters
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

