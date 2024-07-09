package com.example.springjunit.controller;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.entity.Client;
import com.example.springjunit.exception.NoSuchClientException;
import com.example.springjunit.service.impl.JpaClientBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    JpaClientBookService clientBookService;

    @Autowired
    ObjectMapper objectMapper;

    private List<Client> clients;
    private List<ClientDto> clientDtos;

    @BeforeEach
    void init() {
        clients = new ArrayList<>(List.of(
                Client.builder().id(1).name("Linda").phone("89160976967").build(),
                Client.builder().id(2).name("Jake").phone("89166790333").build()
        ));

        clientDtos = new ArrayList<>(List.of(
                new ClientDto("Linda", "89160976967"),
                new ClientDto("Jake", "89166790333")
        ));
    }

    @Test
    void getClientById_shouldReturnOk_ifClientExist() throws Exception {
        int clientId = 1;
        int clientIndex = 0;
        when(clientBookService.getClientById(clientId))
                .thenReturn(clientDtos.get(clientIndex));

        mvc.perform(get("/api/clients/" + clientId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(clientDtos.get(clientIndex).name()))
                .andExpect(jsonPath("$.phone").value(clientDtos.get(clientIndex).phone()));
    }

    @Test
    void getClientById_shouldReturnNoSuchClientException_ifClientNotExist() throws Exception {
        int incorrectId = 3;

        when(clientBookService.getClientById(incorrectId))
                .thenThrow(NoSuchClientException.class);

        mvc.perform(get("/api/clients/" + incorrectId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void getClientByPhone_shouldReturnOk_ifClientExist() throws Exception {
        int clientIndex = 0;
        String phone = clientDtos.get(clientIndex).phone();

        when(clientBookService.getClientByPhone(phone))
                .thenReturn(clientDtos.get(clientIndex));

        mvc.perform(get("/api/clients/phone/" + phone))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(clientDtos.get(clientIndex).name()))
                .andExpect(jsonPath("$.phone").value(clientDtos.get(clientIndex).phone()));
    }

    @Test
    void getClientByPhone_shouldReturnNoSuchClientException_ifClientNotExist() throws Exception {
        String incorrectPhone = "888899999123";

        when(clientBookService.getClientByPhone(incorrectPhone))
                .thenThrow(NoSuchClientException.class);

        mvc.perform(get("/api/clients/phone/" + incorrectPhone))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void getAllClients_shouldReturnOk_ifClientsExist() throws Exception {
        int clientIndex = 0;
        String name = clientDtos.get(clientIndex).name();
        String phone = clientDtos.get(clientIndex).phone();

        when(clientBookService.getAllClients())
                .thenReturn(clientDtos);

        mvc.perform(get("/api/clients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].phone").value(phone));
    }

    @Test
    void getAllClients_shouldReturnNotFound_ifClientsNotExist() throws Exception {
        when(clientBookService.getAllClients())
                .thenReturn(List.of());

        mvc.perform(get("/api/clients"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].name").doesNotExist());
    }

    @Test
    void saveClient_shouldReturnCreated_ifBodyCorrect() throws Exception {
        ClientDto clientDto = new ClientDto("Anna", "89253301230");

        doNothing()
                .when(clientBookService)
                .save(any(ClientDto.class));

        mvc.perform(post("/api/clients")
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateClientByPhone_statusOk_ifClientExist() throws Exception {
        int clientIndex = 0;
        String phone = clientDtos.get(clientIndex).phone();
        ClientDto clientDto = new ClientDto("Anna", "89160975956");

        doNothing()
                .when(clientBookService)
                .updateClient(anyString(), any(ClientDto.class));

        mvc.perform(put("/api/clients/" + phone)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteClientByPhone_statusOk_ifClientExist() throws Exception {
        int clientIndex = 0;
        String phone = clientDtos.get(clientIndex).phone();

        doNothing()
                .when(clientBookService)
                .deleteClient(anyString());

        mvc.perform(delete("/api/clients/" + phone))
                .andDo(print())
                .andExpect(status().isOk());
    }
}