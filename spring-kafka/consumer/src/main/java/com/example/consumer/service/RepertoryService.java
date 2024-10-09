package com.example.consumer.service;

import com.example.consumer.entity.Repertory;
import com.example.consumer.mapper.RepertoryMapper;
import com.example.consumer.repository.RepertoryRepository;
import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepertoryService {
    private final RepertoryRepository repository;
    private final RepertoryMapper mapper;

    @Value("${kafka.topics.messageResult}")
    private String messageResultTopic;

    @Value("${kafka.topics.historyResult}")
    private String historyResultTopic;

    private final KafkaTemplate<Integer, Object> kafkaTemplate;

    @Transactional(readOnly = true)
    public List<UserDto> getMessages(Integer userId) {
        if (userId == null) {
            log.warn("ID = null");
            return Collections.emptyList();
        }

        List<Repertory> userMessages = repository.getAllByUserId(userId);

        if (userMessages.isEmpty()){
            log.warn("No messages found for userId: {}", userId);
            return Collections.emptyList();
        }

        List<UserDto> userDtos = userMessages.stream()
                .map(repertory -> mapper.toDto(repertory))
                .collect(Collectors.toList());

        kafkaTemplate.send(historyResultTopic, userDtos);

        log.info("History for userId: {} sent back to producer", userId);

        return userDtos;
    }

    @Transactional
    public void saveRepertory(UserDto userDto) {
        repository.save(mapper.toEntity(userDto));
    }

    @Transactional
    public void deleteRepertoryOfUser(UserDto userDto) {
        if (userDto.userId() == null) {
            log.warn("ID = null");
            return;
        }

        List<Repertory> repertories = repository.getAllByUserId(userDto.userId());
        if (!repertories.isEmpty()) {
            repository.deleteAll(repertories);
            log.info("Deleted records with id: {}", userDto.userId());

            String resultMessage = "User with ID " + userDto.userId() + " deleted successfully.";
            kafkaTemplate.send(messageResultTopic, resultMessage);
            log.info("Deletion result sent back to producer: {}", userDto.userId());

        } else {
            log.warn("Record with id: {} not found", userDto.userId());
        }
    }

//    public void sendAuditLog(String actionType, Integer userId, String details) {
//        AuditDto auditDto = new AuditDto(actionType, userId, details);
//
//        restTemplate.postForObject(auditServiceUrl, auditDto, AuditDto.class);
//    }
}