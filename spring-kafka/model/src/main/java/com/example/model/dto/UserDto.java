package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record UserDto(
        Integer userId,
        String message,
        LocalDateTime creationDateTime,

        LocalDateTime expirationDateTime
) {
}
