package com.example.springjunit.service.mapper;

import com.example.springjunit.dto.OrderDto;
import com.example.springjunit.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);
}
