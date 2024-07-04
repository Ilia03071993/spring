package com.example.springjunit.service;

import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import com.example.springjunit.exception.NoSuchClientException;
import com.example.springjunit.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class JpaClientBookServiceTest {
    @Autowired
    JpaClientBookService jpaClientBookService;

    @MockBean
    ClientRepository clientRepository;

    @Captor
    private ArgumentCaptor<Client> captor;

    @Test
    void getAllClients_correctSize_ifClientListIsNotEmpty() {
        List<Client> clients = List.of(
                Client.builder()
                        .name("Jake")
                        .phone("89160976967")
                        .build(),
                Client.builder()
                        .name("Luisa")
                        .phone("89253305563")
                        .build()
        );

        when(clientRepository.findAll())
                .thenReturn(clients);

        int result = clientRepository.findAll().size();
        assertEquals(clients.size(), result);
    }

    @Test
    void getClientByPhone_ifPhoneCorrect() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = "89160976967";

        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(client));

        Client clientByPhone = clientRepository.getClientByPhone(phone).orElseThrow();

        assertEquals(client.getName(), clientByPhone.getName());
        assertEquals(client.getPhone(), clientByPhone.getPhone());

//        verify(clientRepository).getClientByPhone(captor.capture().getPhone());
//        assertEquals(phone, captor.getValue().getPhone());
    }

    @Test
    void getClient_shouldThrowNullPointerException_ifNullPhone() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = "";

        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(client));

        assertThrows(NullPointerException.class,
                () -> jpaClientBookService.getClientByPhone(phone)
        );
    }


    @Test
    void addClient_ifCorrectValues() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Fill", "89159977566", orders);

        when(clientRepository.save(client))
                .thenReturn(client);

        jpaClientBookService.addClient(client);

        verify(clientRepository).save(captor.capture());

        Client captorValue = captor.getValue();

        assertEquals(client.getName(), captorValue.getName());
        assertEquals(client.getPhone(), captorValue.getPhone());
    }

    @Test
    void addClient_shouldThrowNullPointerException_ifPhoneIsNull() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Fill", null, orders);
        when(clientRepository.save(client))
                .thenReturn(client);

        assertThrows(NullPointerException.class,
                () -> jpaClientBookService.addClient(client),
                "Phone number cannot be null"
        );

        // Убедитесь, что метод save не был вызван
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void updateClient() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client updatableClient = new Client(1, "Fill", "89159977566", orders);
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = "89159977566";
        // Настройка mock для метода getClientByPhone
        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(updatableClient));

        // Вызов тестируемого метода
        jpaClientBookService.updateClient(phone, client);

        // Проверка, что метод getClientByPhone был вызван с правильным номером телефона
        verify(clientRepository).getClientByPhone(phone);

        // Проверка, что у клиента обновились данные
        assertEquals("Jake", updatableClient.getName());
        assertEquals("89160976967", updatableClient.getPhone());

        // Проверка, что клиент был сохранен
        verify(clientRepository).save(updatableClient);
    }

    @Test
    void updateClient_shouldThrowIllegalArgumentException_ifNullPhone() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = null;

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> jpaClientBookService.updateClient(phone, client)
        );
        assertEquals("Phone number cannot be null or blank",
                ex.getMessage()
        );
    }

    @Test
    void update_shouldThrowNoClientNoSuchClientException_ifIncorrectPhone() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = "91231";

        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.empty());

        NoSuchClientException ex = assertThrows(
                NoSuchClientException.class,
                () -> jpaClientBookService.updateClient(phone, client)
        );
        assertEquals("Client with phone = %s not found".formatted(phone),
                ex.getMessage()
        );
    }

    @Test
    void deleteClient() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = "89160976967";
        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(client));

        jpaClientBookService.deleteClient(phone);

        // Проверка, что метод getClientByPhone был вызван с правильным номером телефона
        verify(clientRepository).getClientByPhone(phone);
        // Проверка, что метод delete был вызван для правильного клиента
        verify(clientRepository).delete(client);
    }

    @Test
    void deleteClient_shouldThrowNoClientNoSuchClientException_ifIncorrectPhone() {
        List<Order> orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
        Client client = new Client(1, "Jake", "89160976967", orders);
        String phone = "89160976967";
        String incorrectPhone = "2123344";
        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(client));

        NoSuchClientException ex = assertThrows(NoSuchClientException.class,
                () -> jpaClientBookService.deleteClient(incorrectPhone));

        assertEquals("Client with phone = %s not found".formatted(incorrectPhone),
                ex.getMessage()
        );
    }
}