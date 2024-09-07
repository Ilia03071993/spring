package com.example.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {
    @GetMapping
    public String getTestString() {
        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject("http://localhost:8081/api/test", String.class);

        assert result != null;
        return result.toUpperCase();
    }
}