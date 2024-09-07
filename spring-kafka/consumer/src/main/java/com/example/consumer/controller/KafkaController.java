package com.example.consumer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaController {
    private static final String STRING_TOPIC = "kafka-string-topic";
    private static final String STRING_TOPIC_RESULT = "kafka-string-result-topic";

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @KafkaListener(topics = STRING_TOPIC)
    public void consumerString(String value) {
        log.info("Consumed from producer: " + value);
        String result = "[result] " + value.toUpperCase();

        kafkaTemplate.send(STRING_TOPIC_RESULT, result);
    }
}