package com.example.producer.sevice;

import com.example.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Value("${message.retention-period}")
    private int retentionPeriod;

    @Value("${kafka.topics.messageRequest}")
    private String messageRequestTopic;

    @Value("${kafka.topics.deleteRequest}")
    private String deleteRequestTopic;

    @Value("${kafka.topics.historyRequest}")
    private String historyRequestTopic;

    private final KafkaTemplate<Integer, Object> kafkaTemplate;

    public void sendMessageToKafka(UserDto userDto) {
        UserDto updateDto = new UserDto(userDto.userId(),
                userDto.message(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(retentionPeriod)
        );

        kafkaTemplate.send(messageRequestTopic, updateDto);
        log.info("Message {} sent to Kafka in topic {}", userDto, messageRequestTopic);
    }

    public void requestMessageHistory(Integer userId) {
        kafkaTemplate.send(historyRequestTopic, userId);
    }

    public void sendDeletedUserToKafka(UserDto userDto) {
        kafkaTemplate.send(deleteRequestTopic, userDto);

        log.info("Delete request for user {} sent to Kafka in topic {}", userDto, deleteRequestTopic);
    }
//    public void sendAuditLog(String actionType, Integer userId, String details) {
//        AuditDto auditDto = new AuditDto(actionType, userId, details);
//
//        restTemplate.postForObject(
//                auditServiceUrl,
//                auditDto, AuditDto.class
//        );
//    }
}