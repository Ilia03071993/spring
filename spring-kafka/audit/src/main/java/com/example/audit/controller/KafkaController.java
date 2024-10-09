package com.example.audit.controller;

import com.example.audit.service.AuditService;
import com.example.model.dto.AuditDto;
import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaController {
    private final AuditService auditService;

    @KafkaListener(topics = "${kafka.topics.messageRequest}")
    public void handleSaveUserMessage(UserDto userDto) {
        auditService.actionAudit(new AuditDto("SAVE_MESSAGE", userDto.userId(), "Message saved in Consumer"));
        log.info("Consumed from producer: " + userDto);
    }

    @KafkaListener(topics = "${kafka.topics.deleteRequest}")
    public void handleDeleteUserMessages(UserDto userDto) {
        auditService.actionAudit(new AuditDto("DELETE_MESSAGE", userDto.userId(), "Message deleted from repertory"));

        log.info("Consumed delete request from producer: " + userDto);

    }

    @KafkaListener(topics = "${kafka.topics.historyRequest}")
    public void handleUserHistoryRequest(Integer userId) {
        log.info("Received history request for user ID: " + userId);

        auditService.actionAudit(new AuditDto("HISTORY_REQUEST", userId, "User requested chat history"));
    }
}
