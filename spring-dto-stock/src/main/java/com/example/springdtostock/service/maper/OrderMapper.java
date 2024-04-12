package com.example.springdtostock.service.maper;

import com.example.springdtostock.dto.OrderDto;
import com.example.springdtostock.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);
}
