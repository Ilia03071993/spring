package com.example.springdtostock.controller;

import com.example.springdtostock.dto.CreateOrderRequest;
import com.example.springdtostock.dto.OrderDto;
import com.example.springdtostock.enums.OrderStatus;
import com.example.springdtostock.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}/sum")
    public BigDecimal getOrderCost(@PathVariable Integer id) {
        return orderService.getOrderCost(id);
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/confirm/{id}/{status}")
    public OrderStatus confirmOrder(@PathVariable Integer id,
                                    @PathVariable String status) {
        return orderService.confirmOrder(id, status);
    }
}
