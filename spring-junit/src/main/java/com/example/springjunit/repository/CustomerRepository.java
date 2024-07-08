package com.example.springjunit.repository;

import com.example.springjunit.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("select c from Customer c where c.email = :email")
    Optional<Customer> findCustomerByEmail(String email);
}