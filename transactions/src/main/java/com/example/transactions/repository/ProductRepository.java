package com.example.transactions.repository;

import com.example.transactions.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity + :quantityIncrease WHERE p.id = :productId")
    int increaseQuantity(@Param("productId") Integer productId, @Param("quantityIncrease") Integer quantityIncrease);

    @Modifying
    @Query("update Product p set p.quantity = p.quantity - :quantityDecrease where p.name = :name")
    void decreaseQuantity(Integer quantityDecrease, String name);
}