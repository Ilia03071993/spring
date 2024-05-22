package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String username,
        String rawPassword,
        String role) {
}