package com.selivanov.orderserivce.dto.v2;

import jakarta.validation.constraints.NotNull;

public record ProductDto(
        @NotNull
        Integer id,
        @NotNull
        String name,
        @NotNull
        Integer quantity
) {
}
