package com.example.transactions.service;

import com.example.transactions.dto.LogDto;
import com.example.transactions.dto.OrderDto;
import com.example.transactions.entity.Order;
import com.example.transactions.exception.NoSuchOrderException;
import com.example.transactions.mapper.OrderMapper;
import com.example.transactions.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuditService auditService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("Order with id = %d is not found".formatted(id)));

        handleMethod("getOrderById");

        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();

        handleMethod("getOrders");

        return orderMapper.toListDto(orders);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public OrderDto addOrder(OrderDto orderDto) {
        if (orderDto.name() == null || orderDto.name().isBlank()) {
            throw new NoSuchOrderException("Name should be not null");
        }

        handleMethod("addOrder");

        Order order = orderMapper.toEntity(orderDto);
        Order saveOrder = orderRepository.save(order);

        return orderMapper.toDto(saveOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void deleteById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("Order with id = %d is not found".formatted(id)));

        handleMethod("deleteById");

        orderRepository.delete(order);
    }

    private void handleMethod(String methodName) {
        auditService.addLog(
                new LogDto(methodName,
                        HttpStatus.OK.toString(),
                        Date.from(ZonedDateTime.now(ZoneId.systemDefault()).toInstant()))
        );
    }
}