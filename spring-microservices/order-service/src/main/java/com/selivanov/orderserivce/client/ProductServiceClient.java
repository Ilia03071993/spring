package com.selivanov.orderserivce.client;

import com.selivanov.orderserivce.dto.v1.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {
    @Value("${product-service-v1.url}")
    private String productServiceUrlV1;

    @Value("${product-service-v2.url}")
    private String productServiceUrlV2;
    // private static final String PRODUCT_SERVICE_URL = "http://localhost:8081";
    // private static final String GET_PRODUCT_URL = "/api/products/{id}";

    private final RestTemplate restTemplate;

    public ProductDto getProductById(Integer productId) {
        return restTemplate.getForObject(productServiceUrlV1 + "%d".formatted(productId), ProductDto.class, productId);
    }

    public com.selivanov.orderserivce.dto.v2.ProductDto getProduct(Integer productId) {
        return restTemplate.getForObject(productServiceUrlV2 + "%d".formatted(productId), com.selivanov.orderserivce.dto.v2.ProductDto.class, productId);
    }
    public void putProductById(Integer productId, com.selivanov.orderserivce.dto.v2.ProductDto productDto) {
        String url = String.format("%s/%d", productServiceUrlV2, productId);
        restTemplate.put(url, productDto);
    }
//    public void putProductById(Integer productId, com.selivanov.orderserivce.dto.v2.ProductDto productDto) {
//        restTemplate.put(productServiceUrlV2 + "%s%d".formatted(productDto,productId), com.selivanov.orderserivce.dto.v2.ProductDto.class, productDto, productId);
//    }
}