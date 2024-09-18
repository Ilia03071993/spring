package com.example.consumer.client;

import com.example.model.dto.AuditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuditClient {
    private final RestTemplate restTemplate;

    private static final String AUDIT_SERVICE_URL = "http://audit-service/api/audit/log";

    public void logAction(String actionType, Integer userId, String details) {
        AuditDto request = new AuditDto(actionType, userId, details);
        restTemplate.postForObject(AUDIT_SERVICE_URL, request, Void.class);
    }
}
