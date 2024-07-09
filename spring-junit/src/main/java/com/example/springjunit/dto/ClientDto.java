package com.example.springjunit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(content = JsonInclude.Include.NON_NULL)
public record ClientDto(
        @Size(min = 2, max = 15, message = "Name should be from 2 to 15 letters")
        String name,
        @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 digits")
        String phone
) {
}
