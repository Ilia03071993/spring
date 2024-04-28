package com.example.springdtostock.controller;

import com.example.springdtostock.dto.CreateOrderRequest;
import com.example.springdtostock.dto.OrderDto;
import com.example.springdtostock.dto.OrderResponse;
import com.example.springdtostock.enums.OrderStatus;
import com.example.springdtostock.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}/sum")
    public ResponseEntity<OrderResponse> getOrderCost(@PathVariable Integer id) {
      return ResponseEntity.ok(orderService.getOrderCost(id));

    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PutMapping("/confirm/{id}/{status}")
    public ResponseEntity<OrderResponse> confirmOrder(@PathVariable Integer id,
                                      @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.confirmOrder(id, status));
    }
}

