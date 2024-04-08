package com.example.springdto.controller;

import com.example.springdto.dto.EmployeeAdditionRequest;
import com.example.springdto.dto.EmployeeDto;
import com.example.springdto.dto.OperationResponse;
import com.example.springdto.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public OperationResponse saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    @PutMapping("/{id}")
    public OperationResponse updateEmployee(@PathVariable Integer id,
                                            @RequestBody EmployeeDto employeeDto) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public OperationResponse deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("/add-department")
    public OperationResponse addEmployeeToDepartment(@RequestBody EmployeeAdditionRequest employeeAdditionRequest) {
        return employeeService.addEmployeeToDepartment(employeeAdditionRequest);
    }

}