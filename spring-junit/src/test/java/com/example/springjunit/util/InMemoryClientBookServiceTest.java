//package com.example.springjunit.util;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//class InMemoryClientBookServiceTest {
//    @Autowired
//    InMemoryClientBookService inMemoryClientBookService;
//
//    @BeforeEach
//    void setUp() {
//        inMemoryClientBookService.getClientsMap().clear();
//    }
//
//    @Test
//    void getAllClients() {
//        addElementsToMap();
//        List<Client> allClients = inMemoryClientBookService.getAllClients();
//        assertEquals(3, allClients.size());
//    }
//
//    @Test
//    void getClientByPhone_ifPhoneCorrect() {
//        addElementsToMap();
//        Client client = inMemoryClientBookService.getClientByPhone("89160976967");
//        assertEquals("Vlad", client.getName());
//    }
//
//    @Test
//    void getClient_ifNullPhone() {
//        addElementsToMap();
//        IllegalArgumentException ex = assertThrows(
//                IllegalArgumentException.class,
//                () -> inMemoryClientBookService.getClientByPhone(null)
//        );
//        assertEquals("Phone number cannot be null", ex.getMessage());
//    }
//
//    @Test
//    void addClient() {
//        Client client = new Client("89159977566", "Fill");
//        inMemoryClientBookService.addClient(client);
//        assertEquals(client, inMemoryClientBookService.getClientByPhone("89159977566"));
//    }
//
//    @Test
//    void add_ifAddNullPhoneToClient() {
//        Client client = new Client(null, "Julia");
//        IllegalArgumentException ex = assertThrows(
//                IllegalArgumentException.class,
//                () -> inMemoryClientBookService.addClient(client)
//        );
//        assertEquals("Phone number cannot be null", ex.getMessage());
//    }
//
//    @Test
//    void updateClient() {
//        addElementsToMap();
//        String phone = "89160976967";
//        Client client = new Client(phone, "Ilia");
//        inMemoryClientBookService.updateClient(phone, client);
//
//        assertEquals(client, inMemoryClientBookService.getClientByPhone(phone));
//    }
//
//    @Test
//    void updateClient_ifNullPhone() {
//        addElementsToMap();
//        String phone = null;
//        IllegalArgumentException ex = assertThrows(
//                IllegalArgumentException.class,
//                () -> inMemoryClientBookService.updateClient(phone, new Client(phone, "Vladislav"))
//        );
//        assertEquals("Phone number cannot be null", ex.getMessage());
//    }
//
//    @Test
//    void deleteClient() {
//        addElementsToMap();
//        String phone = "89160976967";
//        Client client = inMemoryClientBookService.getClientByPhone(phone);
//        inMemoryClientBookService.deleteClient(phone);
//    }
//
//    private void addElementsToMap() {
//        inMemoryClientBookService.addClient(new Client("89160976967", "Vlad"));
//        inMemoryClientBookService.addClient(new Client("89169999985", "Ivan"));
//        inMemoryClientBookService.addClient(new Client("89159977543", "Jake"));
//    }
//}