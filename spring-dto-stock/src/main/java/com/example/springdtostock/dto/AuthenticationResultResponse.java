package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthenticationResultResponse(
        String status,
        String username
) {
}
