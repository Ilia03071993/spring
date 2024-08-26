package com.selivanov.productservice.controller;

import com.selivanov.productservice.dto.v2.ProductDto;
import com.selivanov.productservice.service.ProductServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/products/")
public class ProductControllerV2 {
    private final ProductServiceV2 productServiceV2;

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productServiceV2.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productServiceV2.saveProduct(productDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        productServiceV2.updateProduct(id, productDto);
        return ResponseEntity.ok().build();
    }
}