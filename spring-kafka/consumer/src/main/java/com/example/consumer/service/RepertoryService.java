package com.example.consumer.service;

import com.example.consumer.repository.RepertoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RepertoryService {
    private final RepertoryRepository repository;

    @Value("${message.retention-period}")
    private int retentionPeriod;

    @Scheduled(fixedRate = 12000)
    public void delete() {
        LocalDateTime retentionTime = LocalDateTime.now().plusMinutes(retentionPeriod);
        repository.deleteRepositoryExpirationDateTimeOut(retentionTime);
    }
}
