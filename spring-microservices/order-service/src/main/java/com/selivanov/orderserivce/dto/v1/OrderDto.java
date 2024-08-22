package com.selivanov.orderserivce.dto.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderDto(
        @NotNull
        Integer productId
) {
}
