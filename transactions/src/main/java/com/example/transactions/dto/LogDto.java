package com.example.transactions.dto;

import java.util.Date;

public record LogDto(
        String operation,
        String message,
        Date datetime
) {
}
