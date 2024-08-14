package com.example.springdocker.controller;

import com.example.springdocker.dto.ProductDto;
import com.example.springdocker.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/")
public class ProductController {
    private final ProductService productService;

    @GetMapping("{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PutMapping("{id}")
    public void updateProduct(@PathVariable Integer id,
                              @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}
