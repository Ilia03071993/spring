package com.example.springdtostock.dto;

import java.util.List;
import java.util.Objects;

public record CreateOrderRequest(
        String customerName,
        List<OrderItemRequest> items) {
    public CreateOrderRequest {
        Objects.requireNonNull(customerName, "Customer name must not be null");
        Objects.requireNonNull(items, "Items must not be null");
    }
}

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CreateOrderRequest {
//    private String customerName;
//    private List<OrderItemRequest> items = new ArrayList<>();
//}
