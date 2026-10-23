package com.example.crs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {

        String studentId = loginRequest.get("studentId");
        String password = loginRequest.get("password");

        // Test credentials
        if ("1001".equals(studentId) && "123456".equals(password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", studentId);
            response.put("name", "Test Student");

            return ResponseEntity.ok(response);
        }

        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid Student ID or Password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }*/
}