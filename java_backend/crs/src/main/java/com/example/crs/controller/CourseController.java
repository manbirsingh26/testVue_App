package com.example.crs.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.crs.model.Course;
import com.example.crs.model.CourseRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String title) {
        try {
            List<Course> courses = new ArrayList<Course>();
            if (title == null)
                courseRepository.findAll().forEach(courses::add);
            else
                courseRepository.findByTitleContaining(title).forEach(courses::add);
            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}