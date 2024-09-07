package com.example.consumer.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @SneakyThrows
    public String getString(){
        Thread.sleep(5000);

        return "Hello world";
    }
}
