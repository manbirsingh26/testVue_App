package com.example.crs.model; // <-- Make sure this matches your project's model package!

public class Student {
    private String studentId;
    private String password;
    private String name;
    private String email;

    // Default constructor (Required by Spring Boot for @RequestBody JSON parsing)
    public Student() {
    }

    public Student(String studentId, String password, String name, String email) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}