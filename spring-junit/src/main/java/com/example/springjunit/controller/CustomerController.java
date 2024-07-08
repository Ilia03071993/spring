package com.example.springjunit.controller;

import com.example.springjunit.entity.Customer;
import com.example.springjunit.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        if(!customers.isEmpty()){
            return ResponseEntity.ok(customers);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/header")
    public ResponseEntity<String> headerMethod(@RequestHeader String authorization){
        String reversed = new StringBuilder(authorization).reverse().toString();
        return ResponseEntity
                .ok()
                .header("Authorization", reversed)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomerById(@PathVariable Integer id,
                                                @RequestBody Customer customer){
        customerService.updateCustomerById(customer, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }
}
