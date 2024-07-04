package com.example.springjunit.service;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.entity.Client;
import com.example.springjunit.exception.NoSuchClientException;
import com.example.springjunit.repository.ClientRepository;
import com.example.springjunit.service.mapper.ClientMapper;
import com.example.springjunit.util.ClientBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaClientBookService implements ClientBookService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDto getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client with id = %d not found".formatted(id)));

        return clientMapper.toDto(client);
    }

    public void save(ClientDto clientDto) {
        clientRepository.save(clientMapper.toEntity(clientDto));
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        return clientMapper.toDtoList(clients);
    }

    @Override
    public ClientDto getClientByPhone(String phone) {
        if (phone.isBlank()) {
            throw new NullPointerException();
        }
        Client client = clientRepository.getClientByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        return clientMapper.toDto(client);
    }

    @Override
    public void addClient(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        if (client.getPhone() == null) {
            throw new NullPointerException("Phone number cannot be null");
        }
        clientRepository.save(client);
    }

    @Override
    public void updateClient(String phone, ClientDto clientDto) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be null or blank");
        }

        Client updatableClient = clientRepository.getClientByPhone(phone)
                .orElseThrow(() -> new NoSuchClientException("Client with phone = %s not found".formatted(phone)));

        Client client = clientMapper.toEntity(clientDto);
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
