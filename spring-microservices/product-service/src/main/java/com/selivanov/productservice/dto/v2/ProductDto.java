package com.selivanov.productservice.dto.v2;

import jakarta.validation.constraints.NotNull;

public record ProductDto(
        Integer id,
        String name,
        @NotNull
        Integer quantity
) {
}
