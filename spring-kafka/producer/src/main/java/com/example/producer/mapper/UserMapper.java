package com.example.producer.mapper;

import com.example.producer.dto.UserDto;
import com.example.producer.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @AfterMapping
    default void after(@MappingTarget User user) {
        user.setCreationDateTime(LocalDateTime.now());
        user.setExpirationDateTime(LocalDateTime.now());
    }
}
