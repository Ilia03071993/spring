package com.example.springdtostock.repository;

import com.example.springdtostock.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepository extends JpaRepository<BankCard, Integer> {
}
