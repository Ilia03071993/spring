package com.example.springdocker.service;

import com.example.springdocker.mapper.ProductMapper;
import com.example.springdocker.dto.ProductDto;
import com.example.springdocker.entity.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private List<Product> products = new ArrayList<>();

    public List<ProductDto> getProducts() {
        return productMapper.toListDto(products);
    }

    public ProductDto getProductById(Integer id) {

//        for (Product product : products){
//            if (Objects.equals(product.getId(), id)){
//                return productMapper.toDto(product);
//            }
//        }

        return products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .map(product -> productMapper.toDto(product))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(ProductDto productDto) {
        products.add(productMapper.toEntity(productDto));
    }

    public void updateProduct(Integer id, ProductDto productDto) {
        Product updatableProduct = products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst()
                .orElse(null);

        assert updatableProduct != null;
        updatableProduct.setName(productDto.name());
    }

    public void deleteProduct(Integer id) {
        Product deletableProduct = products.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst()
                .orElse(null);
        products.remove(deletableProduct);
    }

    @PostConstruct
    private void init() {
        products.add(new Product(1, "milk"));
        products.add(new Product(2, "bread"));
    }
}