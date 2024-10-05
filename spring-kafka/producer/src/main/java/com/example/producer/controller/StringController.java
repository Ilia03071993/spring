package com.example.producer.controller;

import com.example.model.dto.UserDto;
import com.example.producer.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/string")
@RequiredArgsConstructor
public class StringController {
    private static final String MESSAGE_REQUEST_TOPIC = "kafka-request";
    private static final String DELETE_REQUEST_TOPIC = "kafka-delete-request";
    private static final String MESSAGE_TOPIC_RESULT = "kafka-message-result-topic";
    private static final String HISTORY_REQUEST_TOPIC = "kafka-history-request-topic";
    private static final String HISTORY_RESULT_TOPIC = "kafka-history-result-topic";

    private final KafkaTemplate<Integer, Object> kafkaTemplate;
    private final UserService service;

    @GetMapping("/history/{userId}")
    public String requestMessageHistory(@PathVariable Integer userId) {
        kafkaTemplate.send(HISTORY_REQUEST_TOPIC, userId);
        log.info("History request for user ID = {} sent to kafka", userId);

        return "Request sent for user ID = " + userId;
    }

    @KafkaListener(topics = HISTORY_RESULT_TOPIC, groupId = "history-result-consumer")
    public void receiveMessagesHistory(List<UserDto> userDtos) {
        log.info("Received chat history from consumer: {}", userDtos);
    }

    @PostMapping
    public UserDto sendMessageToKafka(@RequestBody UserDto userDto) {
        UserDto messageToConsumer = service.sendMessageToKafka(userDto);
        kafkaTemplate.send(MESSAGE_REQUEST_TOPIC, messageToConsumer);
        log.info("Message {} sent to Kafka in topic {}", userDto, MESSAGE_REQUEST_TOPIC);

        return userDto;
    }

    @DeleteMapping
    public UserDto sendDeletedUserToKafka(@RequestBody UserDto userDto) {
//        Object json = jsonUtils.toJson(userDto);
        kafkaTemplate.send(DELETE_REQUEST_TOPIC, userDto);
        log.info("Delete request for user {} sent to Kafka in topic {}", userDto, DELETE_REQUEST_TOPIC);

        return userDto;
    }

    @KafkaListener(topics = MESSAGE_TOPIC_RESULT, groupId = "kafka-result-consumer")
    public void receiveDeletionResult(String result) {
        log.info("Received deletion result from kafka: {}", result);
    }
}