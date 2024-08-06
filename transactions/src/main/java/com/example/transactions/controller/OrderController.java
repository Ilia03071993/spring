package com.example.transactions.controller;

import com.example.transactions.dto.OrderDto;
import com.example.transactions.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders/")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderService.getOrders();

        if (!orders.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("test-header", "test")
                    .body(orders);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.addOrder(orderDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id,
                                         @Valid @RequestBody OrderDto orderDto) {
        orderService.updateOrderById(id, orderDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
