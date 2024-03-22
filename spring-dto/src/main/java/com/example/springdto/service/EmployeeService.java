package com.example.springdto.service;

import com.example.springdto.dto.EmployeeAdditionRequest;
import com.example.springdto.dto.EmployeeDto;
import com.example.springdto.entity.Employee;
import com.example.springdto.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

//    @Transactional(readOnly = true)
//    public Employee findEmployeeById(Integer id) {
//        return employeeRepository.findEmployeeById(id)
//                .orElseThrow(() -> new NoSuchElementException("Employee with id=%d not found".formatted(id)));
//    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee with id=%d not found".formatted(id)));
        return new EmployeeDto(
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment().getName()
        );
    }

    public Employee getEmployeeByIdOrDefault(EmployeeAdditionRequest employeeAdditionRequest) {
        Employee employee;
        if (employeeAdditionRequest.getEmployeeId() != null) {
            employee = employeeRepository
                    .findEmployeeById(employeeAdditionRequest.getEmployeeId())
                    .orElseThrow(() -> new NoSuchElementException("Employee with id=%d not found".formatted(employeeAdditionRequest.getEmployeeId())));
        } else {
            employee = new Employee();
            employee.setName(employeeAdditionRequest.getName());
            employee.setEmail(employeeAdditionRequest.getEmail());
        }
        return employee;
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    public void updateEmployee(Integer id, Employee employee) {
        Employee updatableEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee with id=%d not found".formatted(id)));

        updatableEmployee.setName(employee.getName());
        updatableEmployee.setEmail(employee.getEmail());
        updatableEmployee.setDepartment(employee.getDepartment());
        employeeRepository.save(updatableEmployee);
    }

    @Transactional
    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("Employee with id=%d not found".formatted(id));
        }
        employeeRepository.deleteById(id);
    }
}