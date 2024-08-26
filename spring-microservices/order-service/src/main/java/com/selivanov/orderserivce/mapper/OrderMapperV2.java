package com.selivanov.orderserivce.mapper;

import com.selivanov.orderserivce.dto.v2.OrderDto;
import com.selivanov.orderserivce.entity.Order;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapperV2 {
    Order toEntity(OrderDto orderDto);
    @AfterMapping
    default void after(@MappingTarget Order order){
        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
    }

    OrderDto toDto(Order order);
}
//   Order order = new Order();
//        order.setProductId(product.id());
//        order.setProductQuantity(orderDto.productQuantity());
//        order.setCreatedAt(LocalDateTime.now());
//
//        Order savedOrder = orderRepository.save(order);
//
//
//        Integer updatedQuantity = product.quantity() - orderDto.productQuantity();
//        ProductDto updateProductDto = new ProductDto(product.id(), product.name(), updatedQuantity);
//        productServiceClientV2.updateProductById(product.id(), updateProductDto);
//
//        return orderMapper.toDto(savedOrder);