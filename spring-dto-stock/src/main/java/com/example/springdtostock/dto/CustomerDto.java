package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record CustomerDto(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String name,
        @Positive(message = "Age should not be negative")
        Integer age,
        @Email(message = "Email isn't correct")
        String email,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$", message = "Your password should be 6 to 20 symbols")
        String password,
        @Valid
        @NotNull(message = "Bank card not found")
        BankCardDto bankCard
) {
}