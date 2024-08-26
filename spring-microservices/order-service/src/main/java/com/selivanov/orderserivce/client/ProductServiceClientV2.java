package com.selivanov.orderserivce.client;

import com.selivanov.orderserivce.dto.v2.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductServiceClientV2 {
    @Value("${product-service-v2.url}")
    private String productServiceUrlV2;

    private final RestTemplate restTemplate;

    public ProductDto getProduct(Integer productId) {
        return restTemplate.getForObject(productServiceUrlV2 + "%d".formatted(productId),
                ProductDto.class, productId);
    }

    public void updateProductById(Integer productId, ProductDto productDto) {
        String url = String.format("%s/%d", productServiceUrlV2, productId);
        restTemplate.put(url, productDto);
    }
}
