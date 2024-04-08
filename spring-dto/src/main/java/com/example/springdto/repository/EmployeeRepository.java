package com.example.springdto.repository;

import com.example.springdto.entity.Department;
import com.example.springdto.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e left join fetch e.department")
    List<Employee> findAllEmployees();

    @Query("select e from Employee e left join fetch e.department where e.id = :id")
    Optional<Employee> findEmployeeById(Integer id);

//    @Query("select d from Department d left join fetch d.employees where d.name = :name")
//    Optional<Department> findDepartmentByName(String name);
}