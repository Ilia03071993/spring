package com.example.producer.sevice;

import com.example.model.dto.AuditDto;
import com.example.model.dto.UserDto;
import com.example.producer.entity.User;
import com.example.producer.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    @Value("${message.retention-period}")
    private int retentionPeriod;
    private final RestTemplate restTemplate;
    @Value("${audit-service.url}")
    private String auditServiceUrl;

    public UserDto sendMessageToKafka(UserDto userDto) {
        User user = new User(userDto.userId(),
                userDto.message(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(retentionPeriod)
        );

        sendAuditLog("ADD_MESSAGE", userDto.userId(), "Message: %s, sent to Kafka ".formatted(userDto.message()));

        return userMapper.toDto(user);
    }

    public void sendAuditLog(String actionType, Integer userId, String details) {
        AuditDto auditDto = new AuditDto(actionType, userId, details);

        restTemplate.postForObject(
                auditServiceUrl,
                auditDto, AuditDto.class
        );
    }
}