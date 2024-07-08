package com.example.springjunit.service.abst;

import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllClientOrders(Integer clientId);

    void addOrderToClient(OrderDto order, Integer clientId);

    void deleteOrderFromClient(Integer oderId, Integer clientId);
}
