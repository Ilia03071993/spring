package com.selivanov.orderserivce.service;

import com.selivanov.orderserivce.client.ProductServiceClient;
import com.selivanov.orderserivce.dto.v1.OrderDto;
import com.selivanov.orderserivce.dto.v1.ProductDto;
import com.selivanov.orderserivce.entity.Order;
import com.selivanov.orderserivce.exception.NoSuchProductException;
import com.selivanov.orderserivce.mapper.OrderMapper;
import com.selivanov.orderserivce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductServiceClient serviceClient;
    private final OrderMapper orderMapper;

    public void createOrder(OrderDto orderDto) {
        ProductDto productDto = serviceClient.getProductById(orderDto.productId()); //HTTP call to product-service
        Order order = orderMapper.dtoToOrder(orderDto, productDto);
        orderRepository.save(order);

        //        Order order = new Order();
//        order.setCreatedAt(LocalDateTime.now());
//        order.setProductId(productDto.id());
    }

    public OrderDto createOrder(com.selivanov.orderserivce.dto.v2.OrderDto orderDto) {
        com.selivanov.orderserivce.dto.v2.ProductDto product = serviceClient.getProduct(orderDto.productId());

        if (orderDto.productQuantity() < 0 && orderDto.productQuantity() > product.quantity()) {
            throw new NoSuchProductException("Quantity of product is not correct, actual quantity of product = %d"
                    .formatted(product.quantity()));
        }

        Order order = new Order();
        order.setProductId(product.id());
        order.setProductQuantity(orderDto.productQuantity());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);


        Integer updatedQuantity = product.quantity() - orderDto.productQuantity();
        com.selivanov.orderserivce.dto.v2.ProductDto updateProductDto = new com.selivanov.orderserivce.dto.v2.ProductDto(product.id(), product.name(), updatedQuantity);
        serviceClient.putProductById(product.id(), updateProductDto);

        return orderMapper.toDto(savedOrder);
    }
}