package com.example.springdtostock.controller;

import com.example.springdtostock.dto.ProductDto;
import com.example.springdtostock.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/stock/{category}")
    public List<ProductDto> getProductsByCategoryName(@PathVariable String category) {
        return productService.getProductsByCategoriesName(category);
    }

    @GetMapping("/sort/{minPrice}/{maxPrice}")
    public List<ProductDto> getProductsByPrice(@PathVariable BigDecimal minPrice,
                                               @PathVariable BigDecimal maxPrice) {
        return productService.getProductsByPrice(minPrice, maxPrice);
    }

    @PostMapping("/add/{productId}/{quantity}")
    public void addProductToStock(@PathVariable Integer productId,
                                  @PathVariable Integer quantity) {
        productService.addProductToStock(productId, quantity);
    }

    @PostMapping("/reduce/{productId}/{quantity}")
    public void reduceProductFromStock(@PathVariable Integer productId,
                                       @PathVariable Integer quantity) {
        productService.reduceProductFromStock(productId, quantity);
    }

    @PostMapping
    public void saveProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Integer id,
                              @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}