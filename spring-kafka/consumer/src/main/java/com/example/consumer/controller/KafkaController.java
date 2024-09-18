package com.example.consumer.controller;

import com.example.consumer.service.RepertoryService;
import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaController {
    private final RepertoryService repertoryService;
    private static final String STRING_TOPIC = "json-topic";
    private static final String DELETE_REQUEST_TOPIC = "kafka-delete-request";
    private static final String STRING_TOPIC_RESULT = "kafka-string-result-topic";

    private static final String HISTORY_REQUEST_TOPIC = "kafka-history-request-topic";
    private static final String HISTORY_RESULT_TOPIC = "kafka-history-result-topic";


    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final KafkaTemplate<Integer, Object> kafka;


    @KafkaListener(topics = STRING_TOPIC, groupId = "kafka-consumer")
    public void consumerString(String json) {
        UserDto userDto = repertoryService.consumerStr(json);

        repertoryService.saveRepertory(userDto);

        log.info("Consumed from producer: " + userDto);
    }

    @KafkaListener(topics = DELETE_REQUEST_TOPIC, groupId = "delete-consumer")
    public void consumerObjectStats(String json) {
        UserDto userDto = repertoryService.consumerStr(json);
        repertoryService.deleteRepertory(userDto);
        log.info("Consumed delete request from producer: " + userDto);


        String resultMessage = "User with ID " + userDto.id() + " deleted successfully.";
        kafkaTemplate.send(STRING_TOPIC_RESULT, resultMessage);
        log.info("Deletion result sent back to producer: {}", userDto.id());
    }

    @KafkaListener(topics = HISTORY_REQUEST_TOPIC, groupId = "history-consumer")
    public void consumerHistoryRequest(Integer userId) {
        log.info("Received history request for user ID: " + userId);

        List<UserDto> messages = repertoryService.getMessages(userId);
        kafka.send(HISTORY_RESULT_TOPIC, messages);

        log.info("History for userId: {} sent back to producer", userId);
    }
}