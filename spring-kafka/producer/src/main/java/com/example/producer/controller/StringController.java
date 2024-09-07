package com.example.producer.controller;

import com.example.producer.dto.UserDto;
import com.example.producer.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/string")
@RequiredArgsConstructor
public class StringController {
    private final UserService userService;
    private static final String STRING_TOPIC = "kafka-string-topic";

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    // /api/string?message=text
    @GetMapping
    public String sendMessageToKafka(@RequestParam String message) {
        UserDto userDto = userService.saveUser(message);

        kafkaTemplate.send(STRING_TOPIC, String.valueOf(userDto));

        log.info("Message {} sent to Kafka in topic {}", userDto, STRING_TOPIC);

        return "Message sent to Kafka in topic" + STRING_TOPIC;
    }
}