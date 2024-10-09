package com.example.producer.controller;

import com.example.model.dto.UserDto;
import com.example.producer.dto.ResponseDto;
import com.example.producer.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/string")
@RequiredArgsConstructor
public class StringController {
    private final UserService service;

    @GetMapping("/history/{userId}")
    public ResponseDto requestMessageHistory(@PathVariable Integer userId) {
        service.requestMessageHistory(userId);
        log.info("History request for user ID = {} sent to kafka", userId);

        return new ResponseDto("Request sent for user ID = " + userId);
    }

    @PostMapping
    public UserDto sendMessageToKafka(@RequestBody UserDto userDto) {
        service.sendMessageToKafka(userDto);

        return userDto;
    }

    @DeleteMapping
    public UserDto sendDeletedUserToKafka(@RequestBody UserDto userDto) {
        service.sendDeletedUserToKafka(userDto);

        return userDto;
    }

    @KafkaListener(topics = "${kafka.topics.messageResult}")
    public void receiveDeletionResult(String result) {
        log.info("Received deletion result from kafka: {}", result);
    }

    @KafkaListener(topics = "${kafka.topics.historyResult}")
    public void receiveMessagesHistory(List<UserDto> userDtos) {
        log.info("Received chat history from consumer: {}", userDtos);
    }
}