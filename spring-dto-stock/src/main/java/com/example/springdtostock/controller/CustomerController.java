package com.example.springdtostock.controller;

import com.example.springdtostock.dto.BalanceDto;
import com.example.springdtostock.dto.CustomerDto;
import com.example.springdtostock.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        if (!customers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("test-header", "test")
                    .body(customers);
        }

        return ResponseEntity.notFound().build(); // 404
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceDto> getBalance(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getBalance(id));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerService.saveCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/replenish-balance/{money}")
    public ResponseEntity<?> replenishmentBalance(@PathVariable Integer id,
                                                  @PathVariable BigDecimal money) {
        customerService.replenishmentBalance(id, money);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/withdraw-balance/{money}")
    public ResponseEntity<?> withdrawMoney(@PathVariable Integer id,
                                           @PathVariable BigDecimal money) {
        customerService.withdrawMoney(id, money);

        return ResponseEntity.ok().build();
    }
}