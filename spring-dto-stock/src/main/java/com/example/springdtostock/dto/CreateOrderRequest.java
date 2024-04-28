package com.example.springdtostock.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public record CreateOrderRequest(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String customerName,
        @Valid
        List<OrderItemRequest> items) {
}

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CreateOrderRequest {
//    private String customerName;
//    private List<OrderItemRequest> items = new ArrayList<>();
//}
