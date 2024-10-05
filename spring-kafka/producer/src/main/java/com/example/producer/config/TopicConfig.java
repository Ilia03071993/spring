package com.example.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Value("${kafka.topics.messageRequest}")
    private String messageRequestTopic;

    @Value("${kafka.topics.deleteRequest}")
    private String deleteRequestTopic;

    @Value("${kafka.topics.messageResult}")
    private String messageResultTopic;

    @Value("${kafka.topics.historyRequest}")
    private String historyRequestTopic;

    @Value("${kafka.topics.historyResult}")
    private String historyResultTopic;

    @Bean
    public NewTopic topic() {
        return new NewTopic(messageRequestTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic deleteRequestTopic() {
        return new NewTopic(deleteRequestTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic resultTopic() {
        return new NewTopic(messageResultTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic historyRequestTopic() {
        return new NewTopic(historyRequestTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic historyResultTopic() {
        return new NewTopic(historyResultTopic, 2, (short) 1);
    }
}