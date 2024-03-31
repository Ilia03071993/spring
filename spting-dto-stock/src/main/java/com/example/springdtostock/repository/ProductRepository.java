package com.example.springdtostock.repository;

import com.example.springdtostock.entity.Category;
import com.example.springdtostock.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p left join fetch p.category")
    List<Product> findAllProducts();

    @Query("select p from Product p left join fetch p.category where p.id = :id")
    Optional<Product> getProductById(Integer id);

    @Query("select c from Category c left join fetch c.products where c.name = :name")
    Optional<Category> getCategory(String name);

    @Query("select p from Product p left join fetch p.category where p.category.name = :name")
    List<Product> getProductsByCategoryName(String name);

    @Query("select p from Product p left join fetch p.category where p.price between :minPrice and :maxPrice")
    List<Product> getProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice);
}
