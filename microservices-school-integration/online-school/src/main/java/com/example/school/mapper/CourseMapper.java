package com.example.school.mapper;

import com.example.school.dto.CourseDto;
import com.example.school.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "category.id", source = "categoryId")
    Course toEntity(CourseDto dto);
    @Mapping(target = "categoryId", source = "category.id")
    CourseDto toDto(Course course);

    List<CourseDto> toDtos(List<Course> courses);
}
