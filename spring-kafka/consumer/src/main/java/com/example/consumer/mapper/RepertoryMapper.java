package com.example.consumer.mapper;

import com.example.consumer.dto.RepertoryDto;
import com.example.consumer.entity.Repertory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepertoryMapper {
    Repertory toEntity(RepertoryDto repertoryDto);
}
