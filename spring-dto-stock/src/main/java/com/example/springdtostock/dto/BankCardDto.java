package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record BankCardDto(
        @Size(min = 2, max = 19, message = "Name should be from 2 to 20 symbols")
        String name,
        @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}")
        @Size(min = 13, max = 19, message = "The number of digits in a credit card can range from 13 to 19")
        String number,

        @Pattern(regexp = "\\d{3}")
        String cvc,

        @NotNull
        BigDecimal balance) {
}
