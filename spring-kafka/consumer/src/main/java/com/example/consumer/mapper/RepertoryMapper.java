package com.example.consumer.mapper;

import com.example.consumer.entity.Repertory;
import com.example.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = UserDto.class)
public interface RepertoryMapper {
//    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "userId", expression =  "java(userDto.userId())")
    Repertory toEntity(UserDto userDto);
    @Mapping(target = "userId", source = "userId")
    UserDto toDto (Repertory repertory);
}