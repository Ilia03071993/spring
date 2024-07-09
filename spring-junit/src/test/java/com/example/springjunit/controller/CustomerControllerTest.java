package com.example.springjunit.controller;

import com.example.springjunit.entity.Customer;
import com.example.springjunit.exception.NoSuchCustomerException;
import com.example.springjunit.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    private List<Customer> customers;

    @BeforeEach
    void init() {
        customers = new ArrayList<>(List.of(
                new Customer(1, "Mike", "Lovrov", "mike@mail.ru", 21),
                new Customer(2, "Linda", "Lovrova", "linda@mail.ru", 18)
        ));
    }

    @Test
    void getAllCustomers_shouldReturnOk_ifCustomersExists() throws Exception {
        int customerId = 1;

        when(customerService.getAllCustomers())
                .thenReturn(customers);


        mockMvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(customerId));
    }

    @Test
    void getAllCustomers_shouldReturnNotFound_ifCustomersNotExist() throws Exception {
        when(customerService.getAllCustomers())
                .thenReturn(List.of());


        mockMvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].id").doesNotExist());
    }

    @Test
    void getCustomerById_shouldReturnNotFound_ifCustomerNotExist() throws Exception {
        int customerId = 1;
        when(customerService.getCustomerById(customerId))
                .thenThrow(NoSuchCustomerException.class);

        mockMvc.perform(get("/api/customers/" + customerId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    void saveCustomer_shouldReturnCreated_ifBodyCorrect() throws Exception {
        Customer customer = new Customer(3, "Bob", "Paul", "bob@mail.ru", 30);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}