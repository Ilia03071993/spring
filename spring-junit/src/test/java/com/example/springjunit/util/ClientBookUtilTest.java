package com.example.springjunit.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientBookUtilTest {
    @Autowired
    ClientBookUtil clientBookUtil;

    @BeforeEach
    void setUp() {
        clientBookUtil.clear();
        clientBookUtil.addClient(new Client("89160976967", "Vlad"));
        clientBookUtil.addClient(new Client("89169999985", "Ivan"));
        clientBookUtil.addClient(new Client("89159977543", "Jake"));
    }

    @Test
    void getAllClients() {
        List<Client> allClients = clientBookUtil.getAllClients();
        assertEquals(3, allClients.size());
    }

    @Test
    void getClientByPhone() {
        Client client = clientBookUtil.getClientByPhone("89160976967");
        assertEquals("Vlad", client.getName());
    }

    @Test
    void getClientByNullPhone() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientBookUtil.getClientByPhone(null)
        );
        assertEquals("Phone number cannot be null", ex.getMessage());
    }

    @Test
    void getClientByIncorrectPhone() {
        String phone = "123";
        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> clientBookUtil.getClientByPhone(phone)
        );
        assertEquals("Client with phone " + phone + " not found", ex.getMessage());

    }

    @Test
    void addClient() {
        Client client = new Client("89159977566", "Fill");
        clientBookUtil.addClient(client);
        assertEquals(client, clientBookUtil.getClientByPhone("89159977566"));
    }

    @Test
    void addNullPhoneToClient() {
        Client client = new Client(null, "Julia");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientBookUtil.addClient(client)
        );
        assertEquals("Phone number cannot be null", ex.getMessage());
    }

    @Test
    void updateClient() {
        Client client = new Client("89160976967", "Ilia");
        clientBookUtil.updateClient("89160976967", client);
        assertEquals(client, clientBookUtil.getClientByPhone("89160976967"));
    }

    @Test
    void updateClientNullPhone() {
        String phone = null;
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> clientBookUtil.updateClient(phone, new Client(phone, "Vladislav"))
        );
        assertEquals("Phone number cannot be null", ex.getMessage());
    }

    @Test
    void deleteClient() {
        String phone = "89160976967";
        Client client = clientBookUtil.getClientByPhone(phone);
        clientBookUtil.deleteClient(phone);
    }
}