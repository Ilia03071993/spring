package com.example.springjunit.service;

import com.example.springjunit.entity.Client;
import com.example.springjunit.exception.NoSuchClientException;
import com.example.springjunit.repository.ClientRepository;
import com.example.springjunit.util.ClientBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaClientBookService implements ClientBookService {
    private final ClientRepository clientRepository;

    public Client getClientById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client with id = %d not found".formatted(id)));
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientByPhone(String phone) {
        if (phone.isBlank()) {
            throw new NullPointerException();
        }
        return clientRepository.getClientByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    @Override
    public void addClient(Client client) {
        if (client.getPhone() == null) {
            throw new NullPointerException("Phone number cannot be null");
        }
        clientRepository.save(client);
    }

    @Override
    public void updateClient(String phone, Client client) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }

        Client updatableClient = clientRepository.getClientByPhone(phone)
                .orElseThrow(() -> new NoSuchClientException("Client with phone = %s not found".formatted(phone)));

        updatableClient.setName(client.getName());
        updatableClient.setPhone(client.getPhone());

        clientRepository.save(updatableClient);
    }

    @Override
    public void deleteClient(String phone) {
        Client deletableClient = clientRepository.getClientByPhone(phone)
                .orElseThrow(() -> new NoSuchClientException("Client with phone = %s not found".formatted(phone)));
        clientRepository.delete(deletableClient);
    }
}
