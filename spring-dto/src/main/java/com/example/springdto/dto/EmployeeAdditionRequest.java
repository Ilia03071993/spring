package com.example.springdto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAdditionRequest {
    private Integer employeeId;
    private String name;
    private String email;
    private Integer departmentId;
    private String departmentName;
}