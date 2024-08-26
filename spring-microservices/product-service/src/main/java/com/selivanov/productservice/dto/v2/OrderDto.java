package com.selivanov.productservice.dto.v2;

import jakarta.validation.constraints.NotNull;

public record OrderDto(
        @NotNull
        Integer productId,
        @NotNull
        Integer productQuantity
) {
}
