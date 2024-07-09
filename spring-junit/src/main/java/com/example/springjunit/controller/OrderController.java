package com.example.springjunit.controller;

import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.service.impl.JpaOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final JpaOrderService orderService;

    @GetMapping("/{clientId}")
    public ResponseEntity<List<OrderDto>> getAllClientOrders(@PathVariable Integer clientId) {
        return ResponseEntity.ok(orderService.getAllClientOrders(clientId));
    }

    @PostMapping("/add/{clientId}")
    public ResponseEntity<?> addOrderToClient(@RequestBody OrderDto orderDto,
                                              @PathVariable Integer clientId) {
        orderService.addOrderToClient(orderDto, clientId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{orderIndex}/{clientId}")
    public ResponseEntity<?> deleteOrderFromClient(@PathVariable Integer orderIndex,
                                                   @PathVariable Integer clientId) {
        orderService.deleteOrderFromClient(orderIndex, clientId);

        return ResponseEntity.ok().build();
    } // or better used dto?
}
