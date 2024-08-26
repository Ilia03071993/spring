package com.selivanov.orderserivce.mapper;

import com.selivanov.orderserivce.dto.v1.OrderDto;
import com.selivanov.orderserivce.dto.v1.ProductDto;
import com.selivanov.orderserivce.entity.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapperV1 {
    OrderDto toDto(Order order);
    @Mapping(target = "productId", source = "productDto.id")
    Order dtoToOrder(OrderDto orderDto, ProductDto productDto);

    @AfterMapping
    default void after(@MappingTarget Order order){
        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
    }
}

/**
 *    Order order = new Order();
 *         order.setCreatedAt(LocalDateTime.now());
 *         order.setProductId(productDto.id());
 * */