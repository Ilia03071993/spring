package com.example.transactions.mapper;

import com.example.transactions.dto.LogDto;
import com.example.transactions.entity.Log;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogMapper {
    Log toEntity(LogDto logDto);

    LogDto toDto(Log log);
}
