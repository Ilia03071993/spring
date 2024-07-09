package com.example.springjunit.service.impl;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import com.example.springjunit.repository.OrderRepository;
import com.example.springjunit.mapper.ClientMapper;
import com.example.springjunit.mapper.OrderMapper;
import com.example.springjunit.service.abst.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JpaClientBookService jpaClientBookService;
    private final ClientMapper clientMapper;

    @Override
    public List<OrderDto> getAllClientOrders(Integer clientId) {
        List<Order> orders = orderRepository.getAllOrdersByClientId(clientId);

        return orderMapper.toDtoList(orders);
    }

    @Override
    public void addOrderToClient(OrderDto orderDto, Integer clientId) {
        ClientDto clientDto = jpaClientBookService.getClientById(clientId);
        Client client = clientMapper.toEntity(clientDto);

        Order order = orderMapper.toEntity(orderDto);
        order.setClient(client);

        client.getOrders().add(order);

        jpaClientBookService.save(clientMapper.toDto(client));
    }

    @Override
    public void deleteOrderFromClient(Integer orderIndex, Integer clientId) {
        ClientDto clientDto = jpaClientBookService.getClientById(clientId);
        Client client = clientMapper.toEntity(clientDto);

        Order orderRemove = client.getOrders().get(orderIndex);

        client.getOrders().remove(orderRemove);
        orderRepository.delete(orderRemove);
    }
}
