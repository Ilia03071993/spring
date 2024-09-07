package com.example.consumer.repository;

import com.example.consumer.entity.Repertory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RepertoryRepository extends JpaRepository<Repertory, Integer> {
    @Modifying
    @Query("delete from Repertory r where r.expirationDateTime <= :expirationDateTime")
    void deleteRepositoryExpirationDateTimeOut (LocalDateTime expirationDateTime);
}
