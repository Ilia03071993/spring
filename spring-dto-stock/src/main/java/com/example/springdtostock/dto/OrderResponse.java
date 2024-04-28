package com.example.springdtostock.dto;

import com.example.springdtostock.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class OrderResponse {
    @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
    private String name;
    @NotNull(message = "OrderCost cannot be null")
    @NotBlank(message = "OrderCost cannot be empty or blank")
    @Positive
    private BigDecimal orderCost;
    private OrderStatus orderStatus;
}