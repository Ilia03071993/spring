package com.example.springjunit.controller;

import com.example.springjunit.dto.ClientDto;
import com.example.springjunit.service.impl.JpaClientBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final JpaClientBookService clientBookService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Integer id){
       return ResponseEntity.ok(clientBookService.getClientById(id));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<ClientDto> getClientByPhone(@PathVariable String phone){
      return ResponseEntity.ok(clientBookService.getClientByPhone(phone));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> allClients = clientBookService.getAllClients();
        if (!allClients.isEmpty()) {
            return ResponseEntity.ok(allClients);
        }

        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> saveClient(@RequestBody ClientDto clientDto){
        clientBookService.save(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{phone}")
    public ResponseEntity<?> updateClientByPhone(@PathVariable String phone,
                                                 @RequestBody ClientDto clientDto) {
        clientBookService.updateClient(phone, clientDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{phone}")
    public ResponseEntity<?> deleteClientByPhone(@PathVariable String phone){
        clientBookService.deleteClient(phone);
        return ResponseEntity.ok().build();
    }
}