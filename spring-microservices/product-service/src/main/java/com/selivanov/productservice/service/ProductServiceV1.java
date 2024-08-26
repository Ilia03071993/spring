package com.selivanov.productservice.service;

import com.selivanov.productservice.dto.v1.ProductDto;
import com.selivanov.productservice.entity.Product;
import com.selivanov.productservice.exception.NoSuchProductException;
import com.selivanov.productservice.mapper.ProductMapper;
import com.selivanov.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchProductException("Product with id = %d not fount".formatted(id)));

        return productMapper.toDtoV1(product);
    }

    public ProductDto saveProduct(ProductDto productDto) {
        Product product = productMapper.toEntityV1(productDto);

        return productMapper.toDtoV1(productRepository.save(product));
    }
}