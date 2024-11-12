// package com.coursemate.model;

// @Entity
// public class Module {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;
//     private String description;

//     @ManyToOne
//     @JoinColumn(name = "course_id")
//     private Course course;

//     @ManyToMany
//     @JoinTable(
//         name = "module_teacher",
//         joinColumns = @JoinColumn(name = "module_id"),
//         inverseJoinColumns = @JoinColumn(name = "teacher_id")
//     )
//     private Set<Teacher> assignedTeachers = new HashSet<>();

//     // Getters and Setters
// }
