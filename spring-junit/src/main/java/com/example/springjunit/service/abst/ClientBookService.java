package com.example.springjunit.service.abst;

import com.example.springjunit.dto.ClientDto;

import java.util.List;

public interface ClientBookService {
    List<ClientDto> getAllClients();

    ClientDto getClientByPhone(String phone);

    void addClient(ClientDto clientDto);

    void updateClient(String phone, ClientDto clientDto);

    void deleteClient(String phone);
}