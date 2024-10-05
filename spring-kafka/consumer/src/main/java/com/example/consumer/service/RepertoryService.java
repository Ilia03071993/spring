package com.example.consumer.service;

import com.example.consumer.entity.Repertory;
import com.example.consumer.mapper.RepertoryMapper;
import com.example.consumer.repository.RepertoryRepository;
import com.example.model.dto.AuditDto;
import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepertoryService {
    private final RepertoryRepository repository;
    private final RepertoryMapper mapper;
    private final RestTemplate restTemplate;
    @Value("${audit-service.url}")
    private String auditServiceUrl;

    @Transactional(readOnly = true)
    public List<UserDto> getMessages(Integer userId) {
        if (userId == null) {
            log.warn("ID = null");
        }

        List<Repertory> userMessages = repository.getAllByUserId(userId);
        return userMessages.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveRepertory(UserDto userDto) {
        repository.save(mapper.toEntity(userDto));
        sendAuditLog("SAVE_MESSAGE", userDto.userId(), "Message saved in Consumer");
    }

    @Transactional
    public void deleteRepertoryOfUser(UserDto userDto) {
        if (userDto.userId() == null) {
            log.warn("ID = null");
            return;
        }

        Optional<Repertory> repertory = repository.getRepertoryByUserId(userDto.userId());
        if (repertory.isPresent()) {
            repository.delete(repertory.get());
            log.info("Deleted record with id: {}", userDto.userId());

            sendAuditLog("DELETE_MESSAGE", userDto.userId(), "Message deleted from repertory");
        } else {
            log.warn("Record with id: {} not found", userDto.userId());
        }
    }

    public void sendAuditLog(String actionType, Integer userId, String details) {
        AuditDto auditDto = new AuditDto(actionType, userId, details);

        restTemplate.postForObject(auditServiceUrl, auditDto, AuditDto.class);
    }
}