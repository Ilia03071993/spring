package com.selivanov.productservice.service;

import com.selivanov.productservice.client.OrderServiceClient;
import com.selivanov.productservice.dto.v2.OrderDto;
import com.selivanov.productservice.dto.v2.ProductDto;
import com.selivanov.productservice.entity.Product;
import com.selivanov.productservice.exception.NoSuchProductException;
import com.selivanov.productservice.mapper.ProductMapperV2;
import com.selivanov.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceV2 {
    private final ProductRepository productRepository;
    private final ProductMapperV2 productMapper;
    private final OrderServiceClient orderServiceClient;

    public ProductDto getProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchProductException("Product with id = %d not found".formatted(id)));

        return productMapper.toDto(product);
    }

    public ProductDto saveProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public void updateProduct(Integer productId, ProductDto productDto) {
        Product updatableProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchProductException("Product with id = %d not found".formatted(productId)));

        if (productDto.quantity() < 0) {
            throw new NoSuchProductException("Product = '%s' of quantity cannot be negative, actual quantity = %d"
                    .formatted(updatableProduct.getName(), updatableProduct.getQuantity()));
        }

        if (productDto.quantity() > updatableProduct.getQuantity()) {
            throw new NoSuchProductException("Insufficient quantity for product = '%s'. Available quantity = %d"
                    .formatted(updatableProduct.getName(), updatableProduct.getQuantity()));
        }
        int sumQuantity = updatableProduct.getQuantity() - productDto.quantity();

        updatableProduct.setQuantity(sumQuantity);
        updatableProduct.setName(productDto.name());
        productRepository.save(updatableProduct);
    }
}
