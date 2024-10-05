package com.example.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic topic() {
        return new NewTopic("kafka-request", 2, (short) 1);
    }

    @Bean
    public NewTopic deleteRequestTopic() {
        return new NewTopic("kafka-delete-request", 2, (short) 1);
    }

    @Bean
    public NewTopic resultTopic() {
        return new NewTopic("kafka-string-result-topic", 2, (short) 1);
    }

    @Bean
    public NewTopic historyRequestTopic() {
        return new NewTopic("kafka-history-request-topic", 2, (short) 1);
    }

    @Bean
    public NewTopic historyResultTopic() {
        return new NewTopic("kafka-history-result-topic", 2, (short) 1);
    }
}