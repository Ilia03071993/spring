package com.example.transactions.mapper;

import com.example.transactions.dto.OrderDto;
import com.example.transactions.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);

    OrderDto toDto(Order order);

    List<OrderDto> toListDto(List<Order> orders);
}
