package com.example.school.service;

import com.example.school.dto.CategoryDto;
import com.example.school.dto.CourseDto;
import com.example.school.entity.Category;
import com.example.school.entity.Course;
import com.example.school.mapper.CategoryMapper;
import com.example.school.mapper.CourseMapper;
import com.example.school.repository.CategoryRepository;
import com.example.school.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public CourseDto getCourseById(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course with id = %d not found".formatted(id)));

        return courseMapper.toDto(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courseMapper.toDtos(courses);
    }

    @Transactional
    public void saveCourse(CourseDto courseDto) {
        Category category = categoryRepository.findById(courseDto.categoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with id = %d not found".formatted(courseDto.categoryId())));

        Course course = courseMapper.toEntity(courseDto);
        course.setCategory(category);

        courseRepository.save(course);
    }
    @Transactional
    public void saveCategory(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.toEntity(categoryDto));
    }

    @Transactional
    public void updateCourse(CourseDto courseDto){
        Course courseUpdatable = courseRepository.findById(courseDto.id())
                .orElseThrow(() -> new NoSuchElementException("Course with id = %d not found".formatted(courseDto.id())));
        courseUpdatable.setStartTime(courseDto.startTime());
        courseUpdatable.setFinishTime(courseDto.finishTime());
        courseUpdatable.setPrice(courseDto.price());
        courseUpdatable.setCountSeat(courseDto.countSeat());
        courseUpdatable.setCountAvailableSeat(courseDto.countAvailableSeat());

        Category category = categoryRepository.findById(courseDto.categoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with id = %d not found".formatted(courseDto.categoryId())));
        courseUpdatable.setCategory(category);

        courseRepository.save(courseUpdatable);
    }
    @Transactional
    public void deleteCourse(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course with id = %d not found".formatted(id)));

        courseRepository.deleteById(course.getId());
    }
}