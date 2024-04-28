package com.example.springdtostock.repository;

import com.example.springdtostock.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("select c.bankCard.balance from Customer c where c.id = :id")
    Optional<BigDecimal> getBalance(@Param("id") Integer id);
}
