package com.example.springdtostock.repository;

import com.example.springdtostock.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("select SUM(oi.price * oi.amount) FROM OrderItem oi WHERE oi.order.id = :id")
    Optional<BigDecimal> getOrderCost(@Param("id") Integer id);
}