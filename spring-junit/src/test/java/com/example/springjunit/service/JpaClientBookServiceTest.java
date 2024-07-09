package com.example.springjunit.service;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.entity.Client;
import com.example.springjunit.entity.Order;
import com.example.springjunit.exception.NoSuchClientException;
import com.example.springjunit.mapper.ClientMapper;
import com.example.springjunit.repository.ClientRepository;
import com.example.springjunit.service.impl.JpaClientBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @MockBean
    ClientMapper clientMapper;

    @Captor
    ArgumentCaptor<Client> captor;

    private List<Client> clients;
    private List<ClientDto> clientDtos;
    private List<Order> orders;
    private Client client;
    private ClientDto clientDto;

    @BeforeEach
    void init() {
        clients = new ArrayList<>(List.of(
                Client.builder()
                        .id(1)
                        .name("Jake")
                        .phone("89160976967")
                        .build(),
                Client.builder()
                        .id(2)
                        .name("Luisa")
                        .phone("89253305563")
                        .build()
        ));
        clientDtos = new ArrayList<>(List.of(
                new ClientDto(clients.get(0).getName(), clients.get(0).getPhone()),
                new ClientDto(clients.get(1).getName(), clients.get(1).getPhone())
        ));

        client = clients.get(0);
        clientDto = new ClientDto(client.getName(), client.getPhone());

        orders = new ArrayList<>(List.of(
                Order.builder()
                        .id(1)
                        .name("Product")
                        .build(),
                Order.builder()
                        .id(2)
                        .name("Cleaner")
                        .build()
        ));
    }

    @Test
    void getAllClients_correctSize_ifClientListIsNotEmpty() {
        when(clientRepository.findAll())
                .thenReturn(clients);
        when(clientMapper.toDtoList(clients))
                .thenReturn(clientDtos);

        int result = jpaClientBookService.getAllClients().size();

        assertThat(clientDtos.size()).isEqualTo(result);
        // assertEquals(clientDtos.size(), result);
    }

    @Test
    void getClientById_IfCorrectId() {
        Integer id = 1;

        when(clientRepository.findById(id))
                .thenReturn(Optional.ofNullable(client));
        when(clientMapper.toDto(client))
                .thenReturn(clientDto);

        ClientDto clientById = jpaClientBookService.getClientById(id);
        assertThat(client.getPhone()).isEqualTo(clientById.phone());
        // assertEquals(client != null ? client.getPhone() : null, clientById.phone());
    }

    @Test
    void getClientById_IfIncorrectId() {
        Integer id = 5;
        String exMessage = "Client with id = %d not found".formatted(id);

        when(clientRepository.findById(id))
                .thenReturn(Optional.empty());

        NoSuchClientException ex = assertThrows(NoSuchClientException.class,
                () -> jpaClientBookService.getClientById(id)
        );

        assertThat(exMessage).isEqualTo(ex.getMessage());
        // assertEquals(exMessage, ex.getMessage());
    }

    @Test
    void getClientByPhone_ifPhoneCorrect() {
        String phone = client.getPhone();

        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(client));
        when(clientMapper.toDto(client))
                .thenReturn(clientDto);
        ClientDto clientByPhone = jpaClientBookService.getClientByPhone(phone);

        assertThat(clientDto.name()).isEqualTo(clientByPhone.name());
        assertThat(clientDto.phone()).isEqualTo(clientByPhone.phone());
        // assertEquals(clientDto.name(), clientByPhone.name());
        //  assertEquals(clientDto.phone(), clientByPhone.phone());

//        verify(clientRepository).getClientByPhone(captor.capture().getPhone());
//        assertEquals(phone, captor.getValue().getPhone());
    }

    @Test
    void getClientByPhone_shouldThrowIllegalArgumentException_ifNullPhone() {
        String phone = "";
        assertThatThrownBy(() -> jpaClientBookService.getClientByPhone(phone))
                .isInstanceOf(IllegalArgumentException.class);
//        assertThrows(IllegalArgumentException.class,
//                () -> jpaClientBookService.getClientByPhone(phone)
//        );
    }

    @Test
    void addClient_ifCorrectValues() {
        Client client = new Client(1, "Fill", "89159977566", orders);
        ClientDto clientDto = new ClientDto(client.getName(), client.getPhone());

        when(clientMapper.toEntity(any(ClientDto.class)))
                .thenReturn(client);

        when(clientRepository.save(any(Client.class)))
                .thenReturn(client);

        jpaClientBookService.addClient(clientDto);

        verify(clientRepository).save(captor.capture());

        Client captorValue = captor.getValue();

        assertThat(clientDto.name()).isEqualTo(captorValue.getName());
        assertThat(clientDto.phone()).isEqualTo(captorValue.getPhone());
//        assertEquals(clientDto.name(), captorValue.getName());
//        assertEquals(clientDto.phone(), captorValue.getPhone());
    }

    @Test
    void addClient_shouldThrowNullPointerException_ifPhoneIsNull() {
        ClientDto clientDto = new ClientDto("Fill", null);

        assertThatThrownBy(() -> jpaClientBookService.addClient(clientDto))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Phone number cannot be null");
//        assertThrows(NullPointerException.class,
//                () -> jpaClientBookService.addClient(clientDto),
//                "Phone number cannot be null"
//        );

        //удостовериться метод save не был вызван
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
        Client updatebleClient = new Client(1, "Fill", "89159977566", orders);
        ClientDto clientDtoNew = new ClientDto("Jake", "89160976967");
        Client updatedClient = new Client(updatebleClient.getId(), clientDtoNew.name(), clientDtoNew.phone(), updatebleClient.getOrders());
        String phone = updatebleClient.getPhone();
        // Настройка mock для метода getClientByPhone
        when(clientRepository.getClientByPhone(phone))
                .thenReturn(Optional.of(updatebleClient));
        when(clientMapper.toEntity(any(ClientDto.class)))
                .thenReturn(updatedClient);
        when(clientRepository.save(any(Client.class)))
                .thenReturn(updatedClient);

        // Вызов тестируемого метода
        jpaClientBookService.updateClient(phone, clientDtoNew);

        // Проверка, что метод getClientByPhone был вызван с правильным номером телефона
        verify(clientRepository).getClientByPhone(phone);

        verify(clientRepository).save(captor.capture());
        Client captorValue = captor.getValue();
        assertThat(clientDtoNew.name()).isEqualTo(captorValue.getName());
        assertThat(clientDtoNew.phone()).isEqualTo(captorValue.getPhone());
//        assertEquals(clientDtoNew.name(), captorValue.getName());
//        assertEquals(clientDtoNew.phone(), captorValue.getPhone());
    }

    @Test
    void updateClient_shouldThrowIllegalArgumentException_ifNullPhone() {
        String phone = null;
        ClientDto clientDto = new ClientDto("Jake", "89160976967");

        assertThatThrownBy(() -> jpaClientBookService.updateClient(phone, clientDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Phone number cannot be null or blank");
//        IllegalArgumentException ex = assertThrows(
//                IllegalArgumentException.class,
//                () -> jpaClientBookService.updateClient(phone, clientDto)
//        );
//        assertEquals("Phone number cannot be null or blank",
//                ex.getMessage()
//        );
    }

    @Test
    void update_shouldThrowNoClientNoSuchClientException_ifIncorrectPhone() {
        String incorrectPhone = "91231";

        when(clientRepository.getClientByPhone(incorrectPhone))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> jpaClientBookService.updateClient(incorrectPhone, any(ClientDto.class)))
                .isInstanceOf(NoSuchClientException.class)
                .hasMessage("Client with phone = %s not found".formatted(incorrectPhone));

//        NoSuchClientException ex = assertThrows(
//                NoSuchClientException.class,
//                () -> jpaClientBookService.updateClient(incorrectPhone, any(ClientDto.class))
//        );
//        assertEquals("Client with phone = %s not found".formatted(incorrectPhone),
//                ex.getMessage()
//        );
    }

    @Test
    void deleteClient() {
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
        String incorrectPhone = "2123344";
        when(clientRepository.getClientByPhone(incorrectPhone))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> jpaClientBookService.deleteClient(incorrectPhone))
                .isInstanceOf(NoSuchClientException.class)
                .hasMessage("Client with phone = %s not found".formatted(incorrectPhone));
//        NoSuchClientException ex = assertThrows(NoSuchClientException.class,
//                () -> jpaClientBookService.deleteClient(incorrectPhone));
//
//        assertEquals("Client with phone = %s not found".formatted(incorrectPhone),
//                ex.getMessage()
        //  );
    }
}