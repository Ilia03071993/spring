package com.example.springjunit.repository;

import com.example.springjunit.entity.Customer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void init() {
        Customer customer1 = new Customer
                (1, "Kate", "W.", "kate@mail.ru", 31);
        Customer customer2 = new Customer
                (2, "Jonny", "D.", "jonny@mail.ru", 27);
        customerRepository.saveAll(List.of(customer1, customer2));
    }

    @AfterEach
    void destroy() {
        entityManager.clear();
        entityManager
                .createNativeQuery("TRUNCATE TABLE customers RESTART IDENTITY")
                .executeUpdate();
    }

    @Test
    void findCustomerByEmail_notEmptyOptional_ifCustomerWithSuchEmailExists() {
        String email = "kate@mail.ru";
        String name = "Kate";

        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);

        assertThat(customer.isPresent()).isTrue();
        assertThat(customer.get().getId()).isNotNull();
        assertThat(customer.get().getFirstname()).isEqualTo(name);
        assertThat(customer.get().getEmail()).isEqualTo(email);
    }
}