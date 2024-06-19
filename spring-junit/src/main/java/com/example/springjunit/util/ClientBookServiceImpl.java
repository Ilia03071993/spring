package com.example.springjunit.util;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientBookServiceImpl implements ClientBookService {
    private Map<String, Client> clientsMap = new HashMap<>();

    public Map<String, Client> getClientsMap() {
        return clientsMap;
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clientsMap.values());
    }

    @Override
    public Client getClientByPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }

        return clientsMap.get(phone);
    }

    @Override
    public void addClient(Client client) {
        if (client.getPhone() == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }

        clientsMap.put(client.getPhone(), client);
    }

    @Override
    public void updateClient(String phone, Client client) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        Client updateClient = clientsMap.get(phone);
        updateClient.setName(client.getName());
        updateClient.setPhone(client.getPhone());
    }

    @Override
    public void deleteClient(String phone) {
        clientsMap.remove(phone);
    }
}

/**
 * private Set<Client> clients = new HashSet<>();
 *
 * @Override public List<Client> getAllClients() {
 * return new ArrayList<>(clients);
 * }
 * @Override public Client getClientByPhone(String phone) {
 * if (phone == null) {
 * throw new IllegalArgumentException("Phone number cannot be null");
 * }
 * return clients.stream()
 * .filter(client -> client.getPhone().equalsIgnoreCase(phone))
 * .findFirst()
 * .orElseThrow(() -> new NoSuchElementException("Client with phone " + phone + " not found"));
 * }
 * @Override public void addClient(Client client) {
 * if (client.getPhone() == null) {
 * throw new IllegalArgumentException("Phone number cannot be null");
 * }
 * clients.add(client);
 * }
 * @Override public void updateClient(String phone, Client client) {
 * if (phone == null) {
 * throw new IllegalArgumentException("Phone number cannot be null");
 * }
 * for (Client updatableClient : clients) {
 * if (updatableClient.getPhone().equalsIgnoreCase(phone)) {
 * updatableClient.setName(client.getName());
 * updatableClient.setPhone(client.getPhone());
 * }
 * }
 * }
 * @Override public void deleteClient(String phone) {
 * clients.removeIf(client -> client.getPhone().equalsIgnoreCase(phone));
 * }
 * <p>
 * public void clear() {
 * clients.clear();
 * }
 */