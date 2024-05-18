package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        String username,
        String rawPassword,
        String role) {
}