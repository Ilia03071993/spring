package com.example.consumer.controller;

import com.example.consumer.service.RepertoryService;
import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaController {
    private final RepertoryService repertoryService;

    @KafkaListener(topics = "${kafka.topics.messageRequest}", concurrency = "2")
    public void handleSaveUserMessage(UserDto userDto) {
        repertoryService.saveRepertory(userDto);

        log.info("Consumed from producer: " + userDto);
    }

    @KafkaListener(topics = "${kafka.topics.deleteRequest}")
    public void handleDeleteUserMessages(UserDto userDto) {
        repertoryService.deleteRepertoryOfUser(userDto);

        log.info("Consumed delete request from producer: " + userDto);
    }

    @KafkaListener(topics = "${kafka.topics.historyRequest}")
    public void handleUserHistoryRequest(Integer userId) {
        log.info("Received history request for user ID: " + userId);

        repertoryService.getMessages(userId);
    }
}