package com.example.springdtostock.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDto(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String name,
        @Positive(message = "The quantity must be positive")
        Integer stockQuantity,
        @Positive(message = "The price must be positive")
        BigDecimal price,
        @Size(min = 2, max = 25, message = "Category should be from 2 to 25 letters")
        String category) {
}

//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ProductDto {
//    private String name;
//    private Integer stockQuantity;
//    private BigDecimal price;
//    private String category;
//}