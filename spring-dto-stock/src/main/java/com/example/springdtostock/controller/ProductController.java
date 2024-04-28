package com.example.springdtostock.controller;

import com.example.springdtostock.dto.ProductDto;
import com.example.springdtostock.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("test-header", "test")
                    .body(products);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/stock/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryName(@PathVariable String category) {
        List<ProductDto> products = productService.getProductsByCategoryName(category);
        if (!products.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("test-header", "test")
                    .body(products);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sort/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByPrice(@PathVariable BigDecimal minPrice,
                                                               @PathVariable BigDecimal maxPrice) {
        List<ProductDto> products = productService.getProductsByPrice(minPrice, maxPrice);
        if (!products.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("test-header", "test")
                    .body(products);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<?> addProductToStock(@PathVariable Integer productId,
                                               @PathVariable Integer quantity) {
        productService.addProductToStock(productId, quantity);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reduce/{productId}/{quantity}")
    public ResponseEntity<?> reduceProductFromStock(@PathVariable Integer productId,
                                                    @PathVariable Integer quantity) {
        productService.reduceProductFromStock(productId, quantity);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
                                           @Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok().build();
    }
}