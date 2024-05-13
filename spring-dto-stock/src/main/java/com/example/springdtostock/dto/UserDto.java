package com.example.springdtostock.dto;

public record UserDto(
        String username,
        String rawPassword,
        String role) {
}