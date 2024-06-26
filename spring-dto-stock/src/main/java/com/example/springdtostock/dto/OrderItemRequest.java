package com.example.springdtostock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull(message = "ProductId cannot be null")
        @NotBlank(message = "ProductId cannot be empty or blank")
        @Positive
        Integer productId,
        @NotNull(message = "Amount cannot be null")
        @NotBlank(message = "Amount cannot be empty or blank")
        @Positive
        Integer amount) {
}