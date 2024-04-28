package com.example.springdtostock.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BalanceDto (
        @NotNull
        BigDecimal balance
){
}
