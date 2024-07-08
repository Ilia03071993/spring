package com.example.springjunit.mapper;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientDto clientDto);

    ClientDto toDto(Client client);

    List<ClientDto> toDtoList(List<Client> clients);
}
