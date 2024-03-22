package com.example.springdto.controller;

import com.example.springdto.dto.EmployeeAdditionRequest;
import com.example.springdto.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

//    @GetMapping
//    public List<Department> findAllDepartments() {
//        return departmentService.findAllDepartments();
//    }
//
//    @GetMapping("/{id}")
//    public Department findDepartmentById(@PathVariable Integer id) {
//        return departmentService.findDepartmentById(id);
//    }

    //@GetMapping
//public List<Department> findAllDepartments() {
//    return departmentService.findAllEmployeesWithDepartments();
//}
//    @PostMapping("/{id}/add-employee")
//    public void addEmployeeToDepartment(@PathVariable Integer id,
//                                        @RequestBody Employee employee) {
//        departmentService.addEmployeeToDepartment(id, employee);
//    }


//    @PostMapping
//    public void saveDepartment(@RequestBody Department department) {
//        departmentService.saveDepartment(department);
//    }
//
//    @PutMapping("/{id}")
//    public void updateDepartment(@PathVariable Integer id,
//                                 @RequestBody Department department) {
//        departmentService.updateDepartment(id, department);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteDepartment(@PathVariable Integer id) {
//        departmentService.deleteDepartment(id);
//    }

//    @PostMapping("/add-employee")
//    public EmployeeAdditionResponse addEmployeeToDepartment(@RequestBody EmployeeAdditionRequest employeeAdditionRequest) {
//        return departmentService.addEmployeeToDepartment(employeeAdditionRequest);
//    }

    @PostMapping("/add-employee")
    public void addEmployeeToDepartment(@RequestBody EmployeeAdditionRequest employeeAdditionRequest) {
        departmentService.addEmployeeToDepartment(employeeAdditionRequest);
    }
}