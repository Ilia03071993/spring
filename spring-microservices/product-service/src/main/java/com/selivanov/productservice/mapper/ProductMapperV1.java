package com.selivanov.productservice.mapper;

import com.selivanov.productservice.dto.v1.ProductDto;
import com.selivanov.productservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapperV1 {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}