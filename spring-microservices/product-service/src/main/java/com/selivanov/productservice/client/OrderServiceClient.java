package com.selivanov.productservice.client;

import com.selivanov.productservice.dto.v2.OrderDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrderServiceClient {
    @Value("${order-service.url}")
    private String orderServiceUrl;

    private final RestTemplate restTemplate;

    public OrderDto getOrder (Integer productId){
        return restTemplate.getForObject(orderServiceUrl + "%d".formatted(productId),
                OrderDto.class, productId);
    }
}