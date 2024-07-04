package com.example.springjunit.service;

import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.repository.ClientRepository;
import com.example.springjunit.service.mapper.ClientMapper;
import com.example.springjunit.util.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientBookServiceImpl{
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final OrderService orderService;

    public List<OrderDto> getAllClientOrder(Integer clientId) {
        return orderService.getAllClientOrders(clientId);
    }

    public void addOrderToClient(OrderDto orderDto, Integer clientId){
        orderService.addOrderToClient(orderDto, clientId);
    }

    public void deleteOrderFromClient(Integer orderId, Integer clientId){
        orderService.deleteOrderFromClient(orderId,clientId);
    }
}
