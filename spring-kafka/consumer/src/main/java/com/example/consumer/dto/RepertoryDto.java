package com.example.consumer.dto;

import java.time.LocalDateTime;

public record RepertoryDto(
        String message,

        Integer user_id,

        LocalDateTime creationDateTime,

        LocalDateTime expirationDateTime
) {
}
