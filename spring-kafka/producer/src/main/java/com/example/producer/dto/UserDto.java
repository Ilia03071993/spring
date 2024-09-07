package com.example.producer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record UserDto(
        Integer id,
        String message,
        LocalDateTime creationDateTime,

        LocalDateTime expirationDateTime
) {
}
