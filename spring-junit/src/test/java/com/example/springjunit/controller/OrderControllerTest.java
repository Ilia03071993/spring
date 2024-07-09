package com.example.springjunit.controller;

import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.entity.Order;
import com.example.springjunit.service.abst.OrderService;
import com.example.springjunit.service.impl.JpaOrderService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    JpaOrderService orderService;

    @Autowired
    ObjectMapper objectMapper;
    private List<Order> orders;
    private List<OrderDto> orderDtos;
    @BeforeEach
    void init(){
        orderDtos = new ArrayList<>(List.of(
               new OrderDto("product"),
                new OrderDto("chemistry")
        ));
    }

    @Test
    void getAllClientOrders_shouldReturnOk_ifOrdersExists() throws Exception {
        int clientId = 0;
        int orderIndex = 0;
        when(orderService.getAllClientOrders(clientId))
                .thenReturn(orderDtos);

        mvc.perform(get("/api/orders/" + clientId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].name").value(orderDtos.get(orderIndex).name()));
    }

    @Test
    void addOrderToClient_shouldReturnOk_ifClientExist() throws Exception {
        int orderIndex = 0;
        int clientId = 1;
        OrderDto orderDto = orderDtos.get(orderIndex);

        mvc.perform(post("/api/orders/add/"+ clientId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deleteOrderFromClient_shouldReturnOk_ifClientExist() throws Exception {
        int orderIndex = 0;
        int clientId = 1;

        doNothing()
                .when(orderService)
                .deleteOrderFromClient(anyInt(),anyInt());

        mvc.perform(delete("/api/orders/delete/0/"+ clientId))
                .andDo(print())
                .andExpect(status().isOk());
    } //?
}