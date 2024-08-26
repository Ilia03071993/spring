package com.selivanov.productservice.service;

import com.selivanov.productservice.dto.v1.ProductDto;
import com.selivanov.productservice.entity.Product;
import com.selivanov.productservice.exception.NoSuchProductException;
import com.selivanov.productservice.mapper.ProductMapperV1;
import com.selivanov.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 {
    private final ProductRepository productRepository;
    private final ProductMapperV1 productMapperV1;

    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchProductException("Product with id = %d not fount".formatted(id)));

        return productMapperV1.toDto(product);
    }

    public ProductDto saveProduct(ProductDto productDto) {
        Product product = productMapperV1.toEntity(productDto);

        return productMapperV1.toDto(productRepository.save(product));
    }
}