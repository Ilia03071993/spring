package com.example.producer.mapper;

import com.example.model.dto.UserDto;
import com.example.producer.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface UserMapper {
    @Mapping(target = "creationDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "expirationDateTime", expression = "java(LocalDateTime.now().plusMinutes(5))")
    User toEntity(UserDto userDto);

//    @AfterMapping
//    default void after(@MappingTarget User user) {
//        user.setCreationDateTime(LocalDateTime.now());
//        user.setExpirationDateTime(LocalDateTime.now().plusMinutes(5));
//    }

    UserDto toDto(User user);
}
