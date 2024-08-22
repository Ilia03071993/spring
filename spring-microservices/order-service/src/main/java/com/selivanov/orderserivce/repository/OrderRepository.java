package com.selivanov.orderserivce.repository;

import com.selivanov.orderserivce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
