package com.example.springjunit.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ClientBookServiceImplTest {
    @Autowired
    ClientBookServiceImpl clientBookServiceImpl;

    @BeforeEach
    void setUp() {
        clientBookServiceImpl.clear();
    }

    @Test
    void getAllClients() {
        addElementsToMap();
        List<Client> allClients = clientBookServiceImpl.getAllClients();
        assertEquals(3, allClients.size());
    }

    @Test
    void getClientByPhone_ifPhoneCorrect() {
        addElementsToMap();
        Client client = clientBookServiceImpl.getClientByPhone("89160976967");
        assertEquals("Vlad", client.getName());
    }

    @Test
    void getClient_ifNullPhone() {
        addElementsToMap();
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientBookServiceImpl.getClientByPhone(null)
        );
        assertEquals("Phone number cannot be null", ex.getMessage());
    }

    @Test
    void addClient() {
        Client client = new Client("89159977566", "Fill");
        clientBookServiceImpl.addClient(client);
        assertEquals(client, clientBookServiceImpl.getClientByPhone("89159977566"));
    }

    @Test
    void add_ifAddNullPhoneToClient() {
        Client client = new Client(null, "Julia");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientBookServiceImpl.addClient(client)
        );
        assertEquals("Phone number cannot be null", ex.getMessage());
    }

    @Test
    void updateClient() {
        addElementsToMap();
        Client client = new Client("89160976967", "Ilia");
        clientBookServiceImpl.updateClient("89160976967", client);
        for (Client client1 : clientBookServiceImpl.getAllClients()) {
            System.out.println(client1.getName());
        }
        assertEquals(client, clientBookServiceImpl.getClientByPhone("89160976967"));
    }

    @Test
    void updateClient_ifNullPhone() {
        addElementsToMap();
        String phone = null;
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientBookServiceImpl.updateClient(phone, new Client(phone, "Vladislav"))
        );
        assertEquals("Phone number cannot be null", ex.getMessage());
    }

    @Test
    void deleteClient() {
        addElementsToMap();
        String phone = "89160976967";
        Client client = clientBookServiceImpl.getClientByPhone(phone);
        clientBookServiceImpl.deleteClient(phone);
    }

    private void addElementsToMap() {
        clientBookServiceImpl.addClient(new Client("89160976967", "Vlad"));
        clientBookServiceImpl.addClient(new Client("89169999985", "Ivan"));
        clientBookServiceImpl.addClient(new Client("89159977543", "Jake"));
    }
}