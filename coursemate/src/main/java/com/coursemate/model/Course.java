package com.coursemate.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @ManyToOne
    private Teacher teacher; //establing relationship with Teacher 
    @OneToMany(mappedBy = "course")
    private List<Grade> studentGrades;



    @ManyToMany // A course can have many students and a student can have many courses
    @JoinTable( //Intermediate table that will hold the relationship between the entities
        name = "course_student", //name of join table
        joinColumns = @JoinColumn(name = "course_id"), //column that has primary key of course entity
        inverseJoinColumns = @JoinColumn(name = "student_id") //column that has primary key of student entity
    )
    private List<Student> students;

    // Constructors
    public Course() {}

    public Course(String name, String description, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }

    // Getters and setters
    public List<Grade> getStudentGrades() {
        return studentGrades;
    }
    
    public void setStudentGrades(List<Grade> studentGrades) {
        this.studentGrades = studentGrades;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
