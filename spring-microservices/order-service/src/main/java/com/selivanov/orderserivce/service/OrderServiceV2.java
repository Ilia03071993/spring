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

    public OrderDto createOrder(OrderDto orderDto) {
        ProductDto product = productServiceClientV2.getProduct(orderDto.productId());

        if (orderDto.productQuantity() < 0 && orderDto.productQuantity() > product.quantity()) {
            throw new NoSuchProductException("Quantity of product is not correct, actual quantity of product = %d"
                    .formatted(product.quantity()));
        }
        Order order = orderMapper.toEntity(orderDto);
//        Order order = new Order();
//        order.setProductId(product.id());
//        order.setProductQuantity(orderDto.productQuantity());
//        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);


        Integer updatedQuantity = product.quantity() - orderDto.productQuantity();
        ProductDto updateProductDto = new ProductDto(product.id(), product.name(), updatedQuantity);
        productServiceClientV2.updateProductById(product.id(), updateProductDto);

        return orderMapper.toDto(savedOrder);
    }
}
