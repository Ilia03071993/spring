package com.example.school.mapper;

import com.example.school.dto.CategoryDto;
import com.example.school.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);
}
