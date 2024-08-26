package com.selivanov.orderserivce.service;

import com.selivanov.orderserivce.client.ProductServiceClientV1;
import com.selivanov.orderserivce.dto.v1.OrderDto;
import com.selivanov.orderserivce.dto.v1.ProductDto;
import com.selivanov.orderserivce.entity.Order;
import com.selivanov.orderserivce.mapper.OrderMapperV1;
import com.selivanov.orderserivce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {
    private final OrderRepository orderRepository;
    private final ProductServiceClientV1 serviceClient;
    private final OrderMapperV1 orderMapperV1;

    public void createOrder(OrderDto orderDto) {
        ProductDto productDto = serviceClient.getProductById(orderDto.productId()); //HTTP call to product-service
        Order order = orderMapperV1.dtoToOrder(orderDto, productDto);
        orderRepository.save(order);

        //        Order order = new Order();
//        order.setCreatedAt(LocalDateTime.now());
//        order.setProductId(productDto.id());
    }
}