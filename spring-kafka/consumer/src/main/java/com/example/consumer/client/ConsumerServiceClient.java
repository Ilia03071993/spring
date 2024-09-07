package com.example.consumer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ConsumerServiceClient {
    private final RestTemplate restTemplate;


}