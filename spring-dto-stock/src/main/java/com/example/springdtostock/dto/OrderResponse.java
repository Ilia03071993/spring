package com.example.springdtostock.dto;

import com.example.springdtostock.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String name;
    private BigDecimal orderCost;
    private OrderStatus orderStatus;
}
