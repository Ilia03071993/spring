package com.example.transactions.service;

import com.example.transactions.dto.LogDto;
import com.example.transactions.entity.Log;
import com.example.transactions.mapper.LogMapper;
import com.example.transactions.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final LogRepository logRepository;
    private final LogMapper logMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public LogDto addLog(LogDto logDto) {
        Log log = logMapper.toEntity(logDto);
        Log savelog = logRepository.save(log);

        return logMapper.toDto(savelog);
    }
}