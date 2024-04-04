package com.example.springdtostock.controller;

import com.example.springdtostock.dto.CreateOrderRequest;
import com.example.springdtostock.dto.OrderDto;
import com.example.springdtostock.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order/")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}/sum")
    public BigDecimal getOrderCost(@PathVariable Integer id){
        return orderService.getOrderCost(id);
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
