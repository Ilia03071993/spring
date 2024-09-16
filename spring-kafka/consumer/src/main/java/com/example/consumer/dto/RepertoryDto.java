package com.example.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RepertoryDto(
        @JsonProperty("id")
        Integer userId,
        String message,
        LocalDateTime creationDateTime,
        LocalDateTime expirationDateTime
) {
}
