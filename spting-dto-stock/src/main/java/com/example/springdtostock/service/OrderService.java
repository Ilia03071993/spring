package com.example.springdtostock.service;

import com.example.springdtostock.dto.*;
import com.example.springdtostock.entity.Order;
import com.example.springdtostock.entity.OrderItem;
import com.example.springdtostock.entity.OrderStatus;
import com.example.springdtostock.entity.Product;
import com.example.springdtostock.repository.OrderRepository;
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

    @Transactional(readOnly = true)
    public BigDecimal getOrderCost(Integer id) {
        return orderRepository.getOrderCost(id).orElseThrow();
    }

    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setName(request.getCustomerName());

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : request.getItems()) {
            Product product = productService.findProduct(orderItemRequest.getProductId());
            if (product != null) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setAmount(orderItemRequest.getAmount());
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            } else {
                System.out.println("Order don't create");
            }
        }

        order.setOrderItemList(orderItems);
        Order savedOrder = orderRepository.save(order);
        savedOrder.setStatus(OrderStatus.CONFIRMED);
        return converterToOrderDto(savedOrder);
    }

    private OrderDto converterToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setName(order.getName());
        return orderDto;
    }

//    private OrderItem converterToOrderItem(OrderItemDto itemDto) {
//        OrderItem orderItem = new OrderItem();
//        return orderItem;
//    }
}