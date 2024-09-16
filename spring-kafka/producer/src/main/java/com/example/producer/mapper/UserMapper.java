package com.example.producer.mapper;

import com.example.producer.dto.UserDto;
import com.example.producer.entity.User;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface UserMapper {
  //  @Mapping(target = "creationDateTime", expression =  "java(LocalDateTime.now())")
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

//    @AfterMapping
//    default void after(@MappingTarget User user) {
//        user.setCreationDateTime(LocalDateTime.now());
//    }
}
