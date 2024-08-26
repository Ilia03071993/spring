package com.selivanov.orderserivce.controller;


import com.selivanov.orderserivce.dto.v2.OrderDto;
import com.selivanov.orderserivce.service.OrderServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/orders/")
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        orderServiceV2.createOrder(orderDto);

        return ResponseEntity.ok(orderDto);
    }
}

