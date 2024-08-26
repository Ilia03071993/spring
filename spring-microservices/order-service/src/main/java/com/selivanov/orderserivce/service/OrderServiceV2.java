package com.selivanov.orderserivce.service;

import com.selivanov.orderserivce.client.ProductServiceClientV2;
import com.selivanov.orderserivce.dto.v2.OrderDto;
import com.selivanov.orderserivce.dto.v2.ProductDto;
import com.selivanov.orderserivce.entity.Order;
import com.selivanov.orderserivce.exception.NoSuchProductException;
import com.selivanov.orderserivce.mapper.OrderMapperV2;
import com.selivanov.orderserivce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {
    private final ProductServiceClientV2 productServiceClientV2;
    private final OrderRepository orderRepository;
    private final OrderMapperV2 orderMapper;

    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new NoSuchProductException("The product is not found with id = %d".formatted(id)));

       return orderMapper.toDto(order);
    }

    public OrderDto createOrder(OrderDto orderDto) {
        ProductDto product = productServiceClientV2.getProduct(orderDto.productId());

        if (orderDto.productQuantity() < 0 || orderDto.productQuantity() > product.quantity()) {
            throw new NoSuchProductException("Requested quantity is not valid. Available quantity: %d"
                    .formatted(product.quantity()));
        }
        Order order = orderMapper.toEntity(orderDto);
        Order savedOrder = orderRepository.save(order);

        ProductDto updateProductDto = new ProductDto(product.id(), product.name(), orderDto.productQuantity());
        productServiceClientV2.updateProductById(product.id(), updateProductDto);

        return orderMapper.toDto(savedOrder);
    }
}