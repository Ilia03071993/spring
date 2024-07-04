package com.example.springjunit.repository;

import com.example.springjunit.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("select c from Client c where c.phone = :phone")
    Optional<Client> getClientByPhone(@Param("phone") String phone);
}
