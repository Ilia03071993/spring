package com.example.springdtostock.service;

import com.example.springdtostock.dto.BankCardDto;
import com.example.springdtostock.entity.BankCard;
import com.example.springdtostock.repository.BankCardRepository;
import com.example.springdtostock.service.maper.BankCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BankCardService {
    private final BankCardRepository bankCardRepository;
    private final BankCardMapper bankCardMapper;

    @Transactional
    public void save(BankCardDto bankCardDto) {
        BankCard card = bankCardMapper.toEntity(bankCardDto);
        bankCardRepository.save(card);
    }
}
