package com.example.springjunit.service.impl;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import com.example.springjunit.mapper.ClientMapper;
import com.example.springjunit.mapper.OrderMapper;
import com.example.springjunit.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class JpaOrderServiceTest {
    @Autowired
    JpaOrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    JpaClientBookService clientBookService;

    @MockBean
    OrderMapper orderMapper;

    @MockBean
    ClientMapper clientMapper;
    @Captor
    ArgumentCaptor<ClientDto> clientDtoCapture;
    private List<Order> orders;
    private OrderDto orderDto;
    private ClientDto clientDto;
    private Client client;

    @BeforeEach
    void init() {
        Order order1 = Order.builder()
                .id(1)
                .name("chemical")
                .build();
        Order order2 = Order.builder()
                .id(2)
                .name("product")
                .build();
        orders = new ArrayList<>(List.of(order1, order2));
        orderDto = new OrderDto("chemical");

        client = Client.builder()
                .id(1)
                .name("Fill")
                .phone("89993322169")
                .orders(new ArrayList<>(List.of(order1, order2)))
                .build();
        clientDto = new ClientDto("Fill", "89993322169");
    }

    @Test
    void getAllClientOrders_ordersList_ifOrdersExist() {
        int clientId = 1;
        List<OrderDto> orderDtos = new ArrayList<>(List.of(orderDto));

        when(orderRepository.getAllOrdersByClientId(clientId))
                .thenReturn(orders);
        when(orderMapper.toDtoList(orders))
                .thenReturn(orderDtos);

        List<OrderDto> allClientOrders = orderService.getAllClientOrders(clientId);

        verify(orderRepository).getAllOrdersByClientId(clientId);

        assertThat(allClientOrders.size()).isEqualTo(orderDtos.size());
    }

    @Test
    void addOrderToClient_IfClientExist() {
        int clientId = 1;
        int orderIndex = 0;

        when(clientBookService.getClientById(clientId))
                .thenReturn(clientDto);
        when(clientMapper.toEntity(clientDto))
                .thenReturn(client);
        when(orderMapper.toEntity(orderDto))
                .thenReturn(orders.get(orderIndex));
        when(clientMapper.toDto(client))
                .thenReturn(clientDto);

        orderService.addOrderToClient(orderDto, clientId);

        verify(clientBookService).save(clientDtoCapture.capture());

        ClientDto captureValue = clientDtoCapture.getValue();
        assertThat(captureValue.name()).isEqualTo(clientDto.name());
        assertThat(captureValue.phone()).isEqualTo(clientDto.phone());
    }

    @Test
    void deleteOrderFromClient_IfClientExist() {
        int clientId = 1;
        int orderIndex = 0;

        when(clientBookService.getClientById(clientId))
                .thenReturn(clientDto);
        when(clientMapper.toEntity(clientDto))
                .thenReturn(client);

        orderService.deleteOrderFromClient(orderIndex, clientId);

        verify(orderRepository).delete(orders.get(orderIndex));
    }
}