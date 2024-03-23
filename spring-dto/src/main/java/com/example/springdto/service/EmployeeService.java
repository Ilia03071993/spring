package com.example.springdto.service;

import com.example.springdto.controller.DepartmentController;
import com.example.springdto.dto.EmployeeAdditionRequest;
import com.example.springdto.dto.EmployeeDto;
import com.example.springdto.entity.Department;
import com.example.springdto.entity.Employee;
import com.example.springdto.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    @Transactional(readOnly = true)
    public List<EmployeeDto> findAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAllEmployees();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if (!allEmployees.isEmpty()) {
            for (Employee employee : allEmployees) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setName(employee.getName());
                employeeDto.setEmail(employee.getEmail());
                employeeDto.setDepartment(employee.getDepartment().getName());
                employeeDtoList.add(employeeDto);
            }
        }

        return employeeDtoList;
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
    public void saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());

        Department department = employeeRepository.findDepartmentByName(employeeDto.getDepartment()) //?
                .orElseGet(() -> {
                    Department newDepartment = new Department();
                    newDepartment.setName(employeeDto.getDepartment());
                    return newDepartment;
                });

        employee.setDepartment(department);

        employeeRepository.save(employee);
    }

    @Transactional
    public void updateEmployee(Integer id, EmployeeDto employeeDto) {
        Employee updatableEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee with id=%d not found".formatted(id)));

        updatableEmployee.setName(employeeDto.getName());
        updatableEmployee.setEmail(employeeDto.getEmail());

        Department department = employeeRepository.findDepartmentByName(employeeDto.getDepartment()).orElseThrow(); //?
        updatableEmployee.setDepartment(department);

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