package com.example.springdtostock.service;

import com.example.springdtostock.dto.CreateOrderRequest;
import com.example.springdtostock.dto.OrderDto;
import com.example.springdtostock.dto.OrderItemRequest;
import com.example.springdtostock.dto.OrderResponse;
import com.example.springdtostock.entity.Order;
import com.example.springdtostock.entity.OrderItem;
import com.example.springdtostock.entity.Product;
import com.example.springdtostock.enums.OrderStatus;
import com.example.springdtostock.repository.OrderRepository;
import com.example.springdtostock.service.maper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public OrderResponse getOrderCost(Integer id) {
        BigDecimal cost = orderRepository.getOrderCost(id).orElseThrow();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderCost(cost);
        return orderResponse;
    }

    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setName(request.customerName());

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : request.items()) {
            Product product = productService.findProduct(orderItemRequest.productId());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(orderItemRequest.amount());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItemList(orderItems);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public OrderResponse confirmOrder(Integer orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        OrderStatus orderStatus = (status == OrderStatus.CONFIRMED)
                ? OrderStatus.CONFIRMED
                : OrderStatus.UNCONFIRMED;
        order.setStatus(orderStatus);
        orderRepository.save(order);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderStatus(orderStatus);
        return orderResponse;
    }
}