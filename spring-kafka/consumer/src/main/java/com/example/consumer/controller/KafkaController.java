package com.example.consumer.controller;

import com.example.consumer.dto.RepertoryDto;
import com.example.consumer.service.RepertoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaController {
    private final RepertoryService repertoryService;
    private static final String STRING_TOPIC = "kafka-string-topic";
    private static final String STRING_TOPIC_RESULT = "kafka-string-result-topic";

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = STRING_TOPIC)
    public void consumerString(String value) {
//        String[] split = value.split(", ");
//        System.out.println(Arrays.toString(split));
//        RepertoryDto repertoryDto = new RepertoryDto(
//                split[1],
//                Integer.parseInt(split[0]),
//                LocalDateTime.parse(split[2]),
//                LocalDateTime.parse(split[3])
//        );
        try {
            RepertoryDto repertoryDto = objectMapper.readValue(value, RepertoryDto.class);

            repertoryService.saveRepertory(repertoryDto);

            log.info("Consumed from producer: " + value);

            String result = "[result] " + value.toUpperCase();

            kafkaTemplate.send(STRING_TOPIC_RESULT, result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        repertoryService.delete();
    }
}