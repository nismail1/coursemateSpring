package com.coursemate.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Student extends SystemUser {
    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    

    @ManyToMany(mappedBy = "students")
    private List<Course> enrolledCourses;

    // Constructors
    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "STUDENT");
    }

    // Getters and setters
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
