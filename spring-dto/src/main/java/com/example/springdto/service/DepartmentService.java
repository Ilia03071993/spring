package com.example.springdto.service;

import com.example.springdto.dto.EmployeeAdditionRequest;
import com.example.springdto.dto.EmployeeAdditionResponse;
import com.example.springdto.entity.Department;
import com.example.springdto.entity.Employee;
import com.example.springdto.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;

    @Transactional
    public EmployeeAdditionResponse addEmployeeToDepartment(EmployeeAdditionRequest employeeAdditionRequest) {
        //1. employeeId != null employeeId == null
        //2. departmentId != null departmentId == null'

        Department department;
        if (employeeAdditionRequest.getDepartmentId() == null) {
            department = new Department();
            department.setName(employeeAdditionRequest.getDepartmentName());
            departmentRepository.save(department);
        } else {
            Optional<Department> departmentOptional = departmentRepository
                    .findById(employeeAdditionRequest.getDepartmentId());
            if (departmentOptional.isPresent()) {
                department = departmentOptional.get();
            } else {
                Integer departmentId = employeeAdditionRequest.getDepartmentId();
                return new EmployeeAdditionResponse(false, "Department with id = %d not found".formatted(departmentId));
            }

        }

        Employee employee = employeeService.getEmployeeByIdOrDefault(employeeAdditionRequest);

        department.addEmployee(employee);

        departmentRepository.save(department);

        return new EmployeeAdditionResponse(true, "Success");
    }

    @Transactional
    public Optional<Department> findDepartmentByName(String name) {
        return departmentRepository.findDepartmentByName(name);
    }
//    @Transactional(readOnly = true)
//    public List<Department> findAllDepartments() {
//        return departmentRepository.findAllDepartments();
//    }
//
//    @Transactional(readOnly = true)
//    public Department findDepartmentById(Integer id) {
//        return departmentRepository.findDepartmentById(id)
//                .orElseThrow(() -> new NoSuchElementException("Department with id=%d not found".formatted(id)));
//    }

    //    @Transactional
//    public void addEmployeeToDepartment(Integer id,
//                                        Employee employee) {
//        departmentRepository.findById(id)
//                .ifPresent(department -> {
//                    employee.setDepartment(department);
//                    employeeService.saveEmployee(employee);
//                });
//    }


//    public EmployeeAdditionResponse addEmployeeToDepartment(EmployeeAdditionRequest employeeAdditionRequest) {
//        Department department;
//        if (employeeAdditionRequest.getDepartmentId() == null) {
//            department = new Department();
//            department.setName(employeeAdditionRequest.getDepartmentName());
//        } else {
//            Optional<Department> departmentOptional = departmentRepository
//                    .findById(employeeAdditionRequest.getDepartmentId());
//            if (departmentOptional.isPresent()) {
//                department = departmentOptional.get();
//            } else {
//                Integer departmentId = employeeAdditionRequest.getDepartmentId();
//                return new EmployeeAdditionResponse(false, "Department with id = %d not found".formatted(departmentId));
//            }
//
//        }
//
//        Employee employee = employeeService.getEmployeeByIdOrDefault(employeeAdditionRequest);
//
//        department.addEmployee(employee);
//
//        departmentRepository.save(department);
//
//        return new EmployeeAdditionResponse(true, "Success");
//    }

//    @Transactional
//    public void saveDepartment(Department department) {
//        departmentRepository.save(department);
//    }
//
//    @Transactional
//    public void updateDepartment(Integer id, Department department) {
//        Department updatableDepartment = departmentRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Department with id=%d not found".formatted(id)));
//
//        updatableDepartment.setName(department.getName());
//        departmentRepository.save(updatableDepartment);
//    }
//
//    @Transactional
//    public void deleteDepartment(Integer id) {
//        if (!departmentRepository.existsById(id)) {
//            throw new NoSuchElementException("Department with id=%d not found".formatted(id));
//        }
//        departmentRepository.deleteById(id);
//    }
}