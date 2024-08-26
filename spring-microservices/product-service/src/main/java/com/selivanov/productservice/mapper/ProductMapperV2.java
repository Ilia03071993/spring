package com.selivanov.productservice.mapper;

import com.selivanov.productservice.dto.v2.ProductDto;
import com.selivanov.productservice.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapperV2 {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    List<ProductDto> toListProductDto(List<Product> products);
}
