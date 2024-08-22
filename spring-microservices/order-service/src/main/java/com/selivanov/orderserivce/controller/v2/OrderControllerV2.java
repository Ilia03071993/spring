package com.selivanov.orderserivce.controller.v2;


import com.selivanov.orderserivce.dto.v2.OrderDto;
import com.selivanov.orderserivce.service.OrderService;
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

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        orderService.createOrder(orderDto);

        return ResponseEntity.ok(orderDto);
    }
}

