package com.example.springdtostock.controller;

import com.example.springdtostock.dto.BankCardDto;
import com.example.springdtostock.service.BankCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class BankCardController {
    private final BankCardService bankCardService;

    @PostMapping
    public ResponseEntity<?> save(BankCardDto bankCardDto) {
        bankCardService.save(bankCardDto);

        return ResponseEntity.ok().build();
    }
}
