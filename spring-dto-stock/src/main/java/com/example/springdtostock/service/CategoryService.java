package com.example.springdtostock.service;

import com.example.springdtostock.entity.Category;
import com.example.springdtostock.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.getCategory(name);
    }
}
