package com.example.springdto.controller;

import com.example.springdto.dto.EmployeeDto;
import com.example.springdto.entity.Employee;
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
    public void saveEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDto);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable Integer id,
                               @RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

}