package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResponse(
        String status,
        String username,

        String accessToken,
        String responseToken
) {
}
