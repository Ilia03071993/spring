package com.example.consumer.controller;

import com.example.consumer.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping
    public String getString() {
        return testService.getString();
    }

    @GetMapping("/async")
    public String getStringAsync() {
        String taskId = UUID.randomUUID().toString();
        new Thread(
                () -> {
                    String result = testService.getString();

                }
        );

        return UUID.randomUUID().toString();
    }

    @GetMapping("/async/check-status")
    public String getStringAsyncCheckStatus() {

        return "accepted";
    }
}