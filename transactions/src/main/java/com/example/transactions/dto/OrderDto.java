package com.example.transactions.dto;

import jakarta.validation.constraints.Size;

public record OrderDto(
        @Size(min = 2, max = 15)
        String name
) {
}
