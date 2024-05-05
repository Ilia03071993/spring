package com.example.springdtostock.service.maper;

import com.example.springdtostock.dto.BankCardDto;
import com.example.springdtostock.dto.CustomerDto;
import com.example.springdtostock.entity.BankCard;
import com.example.springdtostock.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDto customerDto);

    CustomerDto toDto(Customer customer);

    List<CustomerDto> toDtoList(List<Customer> customers);

    BankCard bankCardToBankCardDto(BankCardDto bankCard);
}
