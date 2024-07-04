package com.example.springjunit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record OrderDto(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String name
) {
}
