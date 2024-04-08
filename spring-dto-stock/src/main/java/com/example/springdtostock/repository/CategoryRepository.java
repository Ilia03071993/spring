package com.example.springdtostock.repository;

import com.example.springdtostock.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("select c from Category c where c.name = :name")
    Optional<Category> getCategory(String name);
}
