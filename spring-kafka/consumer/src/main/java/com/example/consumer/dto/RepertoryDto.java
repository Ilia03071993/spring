package com.example.consumer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record RepertoryDto(
        Integer id,

        String message,

        Integer userId,

        LocalDateTime creationDateTime,

        LocalDateTime expirationDateTime
) {
}