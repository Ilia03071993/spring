package com.example.producer.controller;

import com.example.model.dto.UserDto;
import com.example.producer.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/string")
@RequiredArgsConstructor
public class StringController {
//        private static final String MESSAGE_REQUEST_TOPIC = "kafka-request";
//    private static final String DELETE_REQUEST_TOPIC = "kafka-delete-request";
//    private static final String MESSAGE_TOPIC_RESULT = "kafka-message-result-topic";
//    private static final String HISTORY_REQUEST_TOPIC = "kafka-history-request-topic";
//    private static final String HISTORY_RESULT_TOPIC = "kafka-history-result-topic";
    @Value("${kafka.topics.messageRequest}")
    private String messageRequestTopic;

    @Value("${kafka.topics.deleteRequest}")
    private String deleteRequestTopic;

    @Value("${kafka.topics.messageResult}")
    private String messageResultTopic;

    @Value("${kafka.topics.historyRequest}")
    private String historyRequestTopic;

    @Value("${kafka.topics.historyResult}")
    private String historyResultTopic;

    private final KafkaTemplate<Integer, Object> kafkaTemplate;
    private final UserService service;

    @GetMapping("/history/{userId}")
    public String requestMessageHistory(@PathVariable Integer userId) {
        kafkaTemplate.send(historyRequestTopic, userId);
        log.info("History request for user ID = {} sent to kafka", userId);

        service.sendAuditLog("GET_HISTORY_MESSAGES", userId, "History request to kafka");
        return "Request sent for user ID = " + userId;
    }

    @KafkaListener(topics = "${kafka.topics.historyResult}", groupId = "history-result-consumer")
    public void receiveMessagesHistory(List<UserDto> userDtos) {
        log.info("Received chat history from consumer: {}", userDtos);
    }

    @PostMapping
    public UserDto sendMessageToKafka(@RequestBody UserDto userDto) {
        UserDto messageToConsumer = service.sendMessageToKafka(userDto);
        kafkaTemplate.send(messageRequestTopic, messageToConsumer);
        log.info("Message {} sent to Kafka in topic {}", userDto, messageRequestTopic);

        return userDto;
    }

    @DeleteMapping
    public UserDto sendDeletedUserToKafka(@RequestBody UserDto userDto) {
        kafkaTemplate.send(deleteRequestTopic, userDto);
        log.info("Delete request for user {} sent to Kafka in topic {}", userDto, deleteRequestTopic);

        service.sendAuditLog("DELETE_MESSAGE", userDto.userId(), "Message delete request sent to Kafka");
        return userDto;
    }

    @KafkaListener(topics = "${kafka.topics.messageResult}", groupId = "kafka-result-consumer")
    public void receiveDeletionResult(String result) {
        log.info("Received deletion result from kafka: {}", result);
    }
}