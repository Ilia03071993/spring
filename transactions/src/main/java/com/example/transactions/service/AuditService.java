package com.example.transactions.service;

import com.example.transactions.entity.Log;
import com.example.transactions.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addLog(Log log) {
        logRepository.save(log);
    }
}