package com.example.crs.controller; // <-- Make sure this matches your controller package!

import com.example.crs.model.Student; // <-- Make sure this matches your Student model package!
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = "http://localhost:5173") // Allows Vue frontend requests (or use origins = "*" if your port differs)
@RestController
@RequestMapping("/api")
public class StudentController {

    // In-memory data store to hold registered students
    private static final Map<String, Student> studentDatabase = new ConcurrentHashMap<>();

    // Initialize default test account (1001 / 123456) when server starts
    static {
        studentDatabase.put("1001", new Student("1001", "123456", "Test Student", "test@student.edu"));
    }

    // Task 3: Refactored Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String studentId = loginRequest.get("studentId");
        String password = loginRequest.get("password");

        // 1. Verify the input student ID and password are not empty
        if (studentId == null || studentId.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student ID and Password cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 2. Check the student existence in the dynamic map
        Student student = studentDatabase.get(studentId);

        // 3. Validate the login info
        if (student != null && student.getPassword().equals(password)) {
            // Success, assemble the data back to Vue
            Map<String, Object> response = new HashMap<>();
            response.put("id", student.getStudentId());
            response.put("name", student.getName());
            
            // Send 200 OK along with student info
            return ResponseEntity.ok(response);
        } else {
            // Login fail
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid Student ID or Password");
            
            // Send 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    // Task 2: Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student newStudent) {
        // 1. Validate data, make sure not null or empty
        if (newStudent.getStudentId() == null || newStudent.getStudentId().trim().isEmpty() ||
            newStudent.getPassword() == null || newStudent.getPassword().trim().isEmpty()) {
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Student ID and Password cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // 2. Check if studentId has already been created
        if (studentDatabase.containsKey(newStudent.getStudentId())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Registration failed: Student ID already exists.");
            
            // Return 409 Conflict status code
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        // 3. If unique StudentID, insert into the simulated database
        studentDatabase.put(newStudent.getStudentId(), newStudent);

        // 4. Return 201 Created status code
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Registration successful!");
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
}