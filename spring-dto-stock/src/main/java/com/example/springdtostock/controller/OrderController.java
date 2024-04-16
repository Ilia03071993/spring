package com.example.springdtostock.controller;

import com.example.springdtostock.dto.CreateOrderRequest;
import com.example.springdtostock.dto.OrderDto;
import com.example.springdtostock.dto.OrderResponse;
import com.example.springdtostock.enums.OrderStatus;
import com.example.springdtostock.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}/sum")
    public OrderResponse getOrderCost(@PathVariable Integer id) {
        return orderService.getOrderCost(id);

    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/confirm/{id}/{status}")
    public OrderResponse confirmOrder(@PathVariable Integer id,
                                      @PathVariable OrderStatus status) {
        return orderService.confirmOrder(id, status);
    }
}

