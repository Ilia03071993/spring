package com.selivanov.orderserivce.client;

import com.selivanov.orderserivce.dto.v1.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductServiceClientV1 {
    @Value("${product-service-v1.url}")
    private String productServiceUrlV1;
    // private static final String PRODUCT_SERVICE_URL = "http://localhost:8081";
    // private static final String GET_PRODUCT_URL = "/api/products/{id}";

    private final RestTemplate restTemplate;

    public ProductDto getProductById(Integer productId) {
        String url = String.format("%d", productServiceUrlV1, productId);

        return restTemplate.getForObject(url, ProductDto.class, productId);
    }
}