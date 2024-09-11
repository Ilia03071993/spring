package com.example.consumer.service;

import com.example.consumer.dto.RepertoryDto;
import com.example.consumer.mapper.RepertoryMapper;
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
    private final RepertoryMapper mapper;

    public void saveRepertory(RepertoryDto repertoryDto) {
        repository.save(mapper.toEntity(repertoryDto));
    }

    @Scheduled(fixedRate = 12000)
    public void delete() {
        repository.deleteRepositoryExpirationDateTimeOut(LocalDateTime.now());
    }
}
