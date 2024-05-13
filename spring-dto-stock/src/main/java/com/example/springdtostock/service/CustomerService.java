package com.example.springdtostock.service;

import com.example.springdtostock.dto.BalanceDto;
import com.example.springdtostock.dto.CustomerDto;
import com.example.springdtostock.entity.Customer;
import com.example.springdtostock.exception.NoSuchBankCardException;
import com.example.springdtostock.exception.NoSuchCustomerException;
import com.example.springdtostock.repository.CustomerRepository;
import com.example.springdtostock.service.maper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.toDtoList(customers);
    }

    @Transactional
    public CustomerDto getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchCustomerException("Customer not found with id = %d".formatted(id)));
        return customerMapper.toDto(customer);
    }

    @Transactional
    public BalanceDto getBalance(Integer id) {
        BigDecimal bigDecimal = customerRepository
                .getBalance(id)
                .orElseThrow(() -> new NoSuchCustomerException("Customer not found with id = %d".formatted(id)));
        return new BalanceDto(bigDecimal);
    }

    @Transactional
    public void saveCustomer(CustomerDto customerDto) {
//        if (customerDto.bankCard() == null) {
//            throw new NoSuchBankCardException("Bank card not found on customer name = %s".formatted(customerDto.name()));
//        }
        //            BankCard bankCard = new BankCard();
//            bankCard.setName(bankCardDto.name());
//            bankCard.setNumber(bankCardDto.number());
//            bankCard.setCvc(bankCardDto.cvc());
//            bankCard.setBalance(bankCardDto.balance());
//
//
//            customer.setBankCard(bankCard);
        Customer customer = customerMapper.toEntity(customerDto);
        customerRepository.save(customer);
    }

    @Transactional
    public void replenishmentBalance(Integer id, BigDecimal money) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchCustomerException("Customer with id = %d not found".formatted(id)));

        BigDecimal balance = customer.getBankCard().getBalance();
        customer.getBankCard().setBalance(balance.add(money));
        customerRepository.save(customer);
    }

    @Transactional
    public void withdrawMoney(Integer id, BigDecimal money) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchCustomerException("Customer with id = %d not found".formatted(id)));

        BigDecimal currentBalance = customer.getBankCard().getBalance();
        if (money.compareTo(currentBalance) > 0) {
            throw new NoSuchBankCardException("Not enough money on your balance");
        }

        customer.getBankCard().setBalance(currentBalance.subtract(money));
        customerRepository.save(customer);
    }
}