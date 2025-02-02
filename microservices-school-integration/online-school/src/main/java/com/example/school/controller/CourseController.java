package com.example.school.controller;

import com.example.school.dto.CategoryDto;
import com.example.school.dto.CourseDto;
import com.example.school.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/")
public class CourseController {
    private final CourseService service;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getCourseById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(service.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(@Valid @RequestBody CourseDto courseDto) {
        service.saveCourse(courseDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        service.saveCategory(categoryDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto) {
        service.updateCourse(courseDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        service.deleteCourse(id);

        return ResponseEntity.ok().build();
    }

}
