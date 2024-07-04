package com.example.springjunit.service.mapper;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientDto clientDto);

    ClientDto toDto(Client client);
}
