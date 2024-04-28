package com.example.springdtostock.service.maper;

import com.example.springdtostock.dto.BankCardDto;
import com.example.springdtostock.entity.BankCard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankCardMapper {
    BankCard toEntity(BankCardDto bankCardDto);

    BankCardDto toDto(BankCard bankCard);
}
