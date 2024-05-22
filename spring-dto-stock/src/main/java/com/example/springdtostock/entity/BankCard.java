package com.example.springdtostock.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bank_cards")
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String number;

    private String cvc;

    private BigDecimal balance;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bankCard")
    private Customer customer;
}