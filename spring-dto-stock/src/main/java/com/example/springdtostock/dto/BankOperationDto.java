package com.example.springdtostock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankOperationDto {
    @NotNull
    @Positive
    Integer id;

    @Positive
    @NotNull
    BigDecimal money;
}
