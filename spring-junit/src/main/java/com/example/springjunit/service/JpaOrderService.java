package com.example.springjunit.service;

import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import com.example.springjunit.repository.OrderRepository;
import com.example.springjunit.service.mapper.OrderMapper;
import com.example.springjunit.util.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JpaClientBookService jpaClientBookService;

    @Override
    public List<OrderDto> getAllClientOrders(Integer clientId) {
        List<Order> orders = orderRepository.getAllOrdersByClientId(clientId);

        return orderMapper.toDtoList(orders);
    }

    @Override
    public void addOrderToClient(OrderDto orderDto, Integer clientId) {
        Client client = jpaClientBookService.getClientById(clientId);
        Order order = orderMapper.toEntity(orderDto);
        order.setClient(client);
        client.getOrders().add(order);

        jpaClientBookService.save(client);
    }

    @Override
    public void deleteOrderFromClient(Integer oderId, Integer clientId) {
        Client client = jpaClientBookService.getClientById(clientId);
        Order orderRemove = client.getOrders().get(oderId);

        client.getOrders().remove(orderRemove);
        orderRepository.delete(orderRemove);
    }
}
