package com.example.model.dto;

public record AuditDto(
        String actionType,
        Integer userId,
        String details
) {
}