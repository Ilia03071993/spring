package com.example.transactions.service;

import com.example.transactions.dto.OrderDto;
import com.example.transactions.entity.Log;
import com.example.transactions.entity.Order;
import com.example.transactions.exception.NoSuchOrderException;
import com.example.transactions.mapper.OrderMapper;
import com.example.transactions.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("Order with id = %d is not found".formatted(id)));

        handleMethod("getOrderById");

        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();

        handleMethod("getOrders");

        return orderMapper.toListDto(orders);
    }

    @Transactional
    public OrderDto addOrder(OrderDto orderDto) {
        if (orderDto.name() == null || orderDto.name().isBlank()) {
            throw new NoSuchOrderException("Name should be not null");
        }

        handleMethod("addOrder");

        Order order = orderMapper.toEntity(orderDto);
        Order saveOrder = orderRepository.save(order);

        return orderMapper.toDto(saveOrder);
    }

    @Transactional
    public void updateOrderById(Integer id, OrderDto orderDto) {
        if (orderDto.name() == null || orderDto.name().isBlank()) {
            throw new NoSuchOrderException("Name should be not null");
        }

        handleMethod("updateOrderById");

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("Order with id = %d is not found".formatted(id)));
        order.setName(orderDto.name());

        orderRepository.save(order);
    }

    @Transactional
    public void deleteById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("Order with id = %d is not found".formatted(id)));

        handleMethod("deleteById");

        orderRepository.delete(order);
    }

    private void handleMethod(String methodName) {
        auditService.addLog(
                new Log(methodName,
                        HttpStatus.OK.toString()
                )
        );
    }
}