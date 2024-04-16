package com.example.springdtostock.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductDto(
        String name,
        Integer stockQuantity,
        BigDecimal price,
        String category) {
    public ProductDto {
        Objects.requireNonNull(category, "Category must not be null");
    }
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