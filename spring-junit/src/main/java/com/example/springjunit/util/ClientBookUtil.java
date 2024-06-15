package com.example.springjunit.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class ClientBookUtil implements ClientBookService {
    private Set<Client> clients = new HashSet<>();

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    @Override
    public Client getClientByPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        return clients.stream()
                .filter(client -> client.getPhone().equalsIgnoreCase(phone))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Client with phone " + phone + " not found"));
    }

    @Override
    public void addClient(Client client) {
        if (client.getPhone() == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        clients.add(client);
    }

    @Override
    public void updateClient(String phone, Client client) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        for (Client updatableClient : clients) {
            if (updatableClient.getPhone().equalsIgnoreCase(phone)) {
                updatableClient.setName(client.getName());
                updatableClient.setPhone(client.getPhone());
            }
        }
    }

    @Override
    public void deleteClient(String phone) {
        clients.removeIf(client -> client.getPhone().equalsIgnoreCase(phone));
    }

    public void clear() {
        clients.clear();
    }
}
