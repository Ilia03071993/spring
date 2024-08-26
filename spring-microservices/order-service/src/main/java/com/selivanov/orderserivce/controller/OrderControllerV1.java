package com.selivanov.orderserivce.controller;

import com.selivanov.orderserivce.dto.v1.OrderDto;
import com.selivanov.orderserivce.service.OrderServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders/")
public class OrderControllerV1 {
    private final OrderServiceV1 orderServiceV1;

    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto) {
        orderServiceV1.createOrder(orderDto);
    }
}