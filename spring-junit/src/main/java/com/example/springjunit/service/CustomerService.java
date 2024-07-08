package com.example.springjunit.service;

import com.example.springjunit.entity.Customer;
import com.example.springjunit.exception.NoSuchCustomerException;
import com.example.springjunit.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id){
        return customerRepository.findById(id)
                .orElseThrow(()->new NoSuchCustomerException(
                        "Customer with id = '%d' not found".formatted(id)
                ));
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void updateCustomerById(Customer newCustomer, Integer id){
        Customer updatedCustomer = customerRepository.findById(id)
                .orElseThrow(()->new NoSuchCustomerException(
                        "Customer with id = '%d' not found".formatted(id)));
        updatedCustomer.setFirstname(newCustomer.getFirstname());
        updatedCustomer.setLastname(newCustomer.getLastname());
        updatedCustomer.setAge(newCustomer.getAge());
        customerRepository.save(updatedCustomer);
    }

    public void deleteCustomerById(Integer id){
        if(!customerRepository.existsById(id)){
            throw new NoSuchCustomerException(
                    "Customer with id = '%d' not found".formatted(id));
        }

        customerRepository.deleteById(id);
    }
}
