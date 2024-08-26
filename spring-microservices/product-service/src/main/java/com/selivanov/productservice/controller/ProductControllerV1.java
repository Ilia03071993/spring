package com.selivanov.productservice.controller;

import com.selivanov.productservice.dto.v1.ProductDto;
import com.selivanov.productservice.service.ProductServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/")
public class ProductControllerV1 {
    private final ProductServiceV1 productServiceV1;

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productServiceV1.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productServiceV1.saveProduct(productDto));
    }
}