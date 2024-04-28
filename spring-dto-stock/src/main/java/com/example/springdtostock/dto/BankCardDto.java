package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record BankCardDto(
        @Size(min = 2, max = 19, message = "Name should be from 2 to 20 symbols")
        String name,

        @Size(min = 13, max = 19, message = "The number of digits in a credit card can range from 13 to 19")
        String number,

        @Positive(message = "CVC should be positive")
        @Digits(integer = 3, fraction = 0, message = "CVC should have 3 digits")
        Integer cvc,

        @NotNull
        BigDecimal balance) {
}
