package com.example.springdtostock.dto;

import jakarta.validation.constraints.Size;

public record OrderDto(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String name) {

}

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class OrderDto {
//    private String name;
//}