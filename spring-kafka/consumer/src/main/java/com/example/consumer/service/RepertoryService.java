package com.example.consumer.service;

import com.example.consumer.client.AuditClient;
import com.example.consumer.entity.Repertory;
import com.example.consumer.mapper.RepertoryMapper;
import com.example.consumer.repository.RepertoryRepository;
import com.example.consumer.utils.JsonUtils;
import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepertoryService {
    private final RepertoryRepository repository;
    private final RepertoryMapper mapper;
    private final JsonUtils jsonUtils;
    private final AuditClient auditClient;
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
        auditClient.logAction("SAVE_MESSAGE", userDto.id(), "Message saved in Consumer");
    }

    @Transactional
    public void deleteRepertory(UserDto userDto) {
        if (userDto.id() == null) {
            log.warn("ID = null");
            return;
        }

        Optional<Repertory> repertory = repository.findById(userDto.id());
        if (repertory.isPresent()) {
            repository.delete(repertory.get());
            log.info("Deleted record with id: {}", userDto.id());
        } else {
            log.warn("Record with id: {} not found", userDto.id());
        }

        auditClient.logAction("DELETE_MESSAGE", userDto.id(), "Message deleted in Consumer");
    }

    public UserDto consumerStr(String json) {
        return jsonUtils.fromJson(json, UserDto.class);
    }
}