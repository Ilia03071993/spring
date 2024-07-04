package com.example.springjunit.repository;

import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.client.id = :clientId")
    List<Order> getAllOrdersByClientId(@Param("clientId") Integer clientId);
}