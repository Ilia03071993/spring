package com.example.springjunit.util;

import java.util.List;

public interface ClientBookService {
    List<Client> getAllClients();

    Client getClientByPhone(String phone);

    void addClient(Client client);

    void updateClient(String phone, Client client);

    void deleteClient(String phone);
}