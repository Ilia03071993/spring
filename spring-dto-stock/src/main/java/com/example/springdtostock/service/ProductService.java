package com.example.springdtostock.service;

import com.example.springdtostock.dto.ProductDto;
import com.example.springdtostock.entity.Category;
import com.example.springdtostock.entity.Product;
import com.example.springdtostock.repository.ProductRepository;
import com.example.springdtostock.service.maper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductDto getProductById(Integer id) {
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchElementException("Product not found with id = %d".formatted(id)));

        return new ProductDto(product.getName(), product.getStockQuantity(), product.getPrice(), product.getCategory().getName());
    }

    @Transactional(readOnly = true)
    public Product findProduct(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id = %d".formatted(id)));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAllProducts();
        return productMapper.toDtoList(productList);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategoryName(String name) {
        List<Product> productsByCategoryName = productRepository.getProductsByCategoryName(name);
        return productMapper.toDtoList(productsByCategoryName);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> productsByCategoryName = productRepository.getProductsByPrice(minPrice, maxPrice);
        return productMapper.toDtoList(productsByCategoryName);
    }

    @Transactional
    public void saveProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
    }

    @Transactional
    public void addProductToStock(Integer id, Integer quantity) {
        Product updatableProduct = productRepository.getProductById(id).orElseThrow(() -> new NoSuchElementException("Product not found with id = %d".formatted(id)));

        Integer stockQuantity = updatableProduct.getStockQuantity();
        updatableProduct.setStockQuantity(stockQuantity + quantity);

        productRepository.save(updatableProduct);
    }

    @Transactional
    public void reduceProductFromStock(Integer id, Integer quantity) {
        Product updatableProduct = productRepository.getProductById(id).orElseThrow(() -> new NoSuchElementException("Product not found with id = %d".formatted(id)));

        Integer stockQuantity = updatableProduct.getStockQuantity();
        if (stockQuantity < quantity) {
            throw new IllegalArgumentException("The stock quantity isn't enough");
        }

        updatableProduct.setStockQuantity(stockQuantity - quantity);

        productRepository.save(updatableProduct);
    }

    @Transactional
    public void updateProduct(Integer id, ProductDto productDto) {
        Product udatableProduct = productRepository.getProductById(id).orElseThrow(() -> new NoSuchElementException("Product not found with id = %d".formatted(id)));

        Category category = categoryService.getCategoryByName(productDto.category()).orElseThrow(() -> new NoSuchElementException("Category with id=%d not found".formatted(id)));
        udatableProduct.setCategory(category);
        productMapper.update(udatableProduct, productDto);
        productRepository.save(udatableProduct);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product with id=%d not found".formatted(id));
        }
        productRepository.deleteById(id);
    }

//    private List<ProductDto> mapToProductDto(List<Product> productsByCategoryName) {
//        List<ProductDto> productDtoList = new ArrayList<>();
//        if (!productsByCategoryName.isEmpty()) {
//            for (Product product : productsByCategoryName) {
//                ProductDto productDto = new ProductDto();
//                productDto.setName(product.getName());
//                productDto.setPrice(product.getPrice());
//                productDto.setStockQuantity(product.getStockQuantity());
//                productDto.setCategory(product.getCategory().getName());
//                productDtoList.add(productDto);
//            }
//        }
//
//        return productDtoList;
//    }
}