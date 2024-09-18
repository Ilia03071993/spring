package com.example.producer.controller;

import com.example.model.dto.UserDto;
import com.example.producer.sevice.UserService;
import com.example.producer.util.JsonUtils;
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
    private final UserService userService;
    private static final String STRING_TOPIC = "json-topic";
    private static final String DELETE_REQUEST_TOPIC = "kafka-delete-request";
    private static final String STRING_TOPIC_RESULT = "kafka-string-result-topic";
    private static final String HISTORY_REQUEST_TOPIC = "kafka-history-request-topic";
    private static final String HISTORY_RESULT_TOPIC = "kafka-history-result-topic";

    private final KafkaTemplate<Integer, Object> kafkaTemplate;
    private final JsonUtils jsonUtils;


    @GetMapping("/history/{userId}")
    public String requestMessageHistory(@PathVariable Integer userId) {
        kafkaTemplate.send(HISTORY_REQUEST_TOPIC, userId);
        log.info("History request for user ID = {} sent to kafka", userId);

        return "Request sent for user ID = " + userId;
    }

    @KafkaListener(topics = HISTORY_RESULT_TOPIC, groupId = "history-result-consumer")
    public void receiveMessagesHistory(List<UserDto> userDtos){
        log.info("Received chat history from consumer: {}", userDtos);
    }

    @PostMapping
    public UserDto sendMessageToKafka(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.saveUser(userDto);
        Object json = jsonUtils.toJson(savedUserDto);
        kafkaTemplate.send(STRING_TOPIC, json);
        log.info("Message {} sent to Kafka in topic {}", json, STRING_TOPIC);

        return userDto;
    }

    @DeleteMapping
    public UserDto sendDeletedUserToKafka(@RequestBody UserDto userDto) {
        Object json = jsonUtils.toJson(userDto);
        kafkaTemplate.send(DELETE_REQUEST_TOPIC, json);
        log.info("Delete request for user {} sent to Kafka in topic {}", json, DELETE_REQUEST_TOPIC);

        return userDto;
    }

    @KafkaListener(topics = STRING_TOPIC_RESULT, groupId = "kafka-result-consumer")
    public void receiveDeletionResult(String result) {
        log.info("Received deletion result from kafka: {}", result);
    }



}