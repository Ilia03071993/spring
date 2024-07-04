package com.example.springjunit.util;

import com.example.springjunit.entity.Client;

import java.util.List;

public interface ClientBookService {
    List<Client> getAllClients();

    Client getClientByPhone(String phone);

    void addClient(com.example.springjunit.entity.Client client);

    void updateClient(String phone, com.example.springjunit.entity.Client client);

    void deleteClient(String phone);
}