package com.selivanov.orderserivce.controller;


import com.selivanov.orderserivce.dto.v2.OrderDto;
import com.selivanov.orderserivce.service.OrderServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/orders/")
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderServiceV2.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        orderServiceV2.createOrder(orderDto);

        return ResponseEntity.ok(orderDto);
    }
}

