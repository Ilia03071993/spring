package com.selivanov.productservice.dto.v2;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductDto(
        Integer id,
        String name,
        @NotNull
        Integer quantity
) {
}
