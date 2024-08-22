package com.selivanov.productservice.mapper;

import com.selivanov.productservice.dto.v1.ProductDto;
import com.selivanov.productservice.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDtoV1(Product product);

    Product toEntityV1(ProductDto productDto);

    List<ProductDto> toListProductDtoV1(List<Product> products);


    com.selivanov.productservice.dto.v2.ProductDto toDtoV2(Product product);

    Product toEntityV2(com.selivanov.productservice.dto.v2.ProductDto productDto);

    List<com.selivanov.productservice.dto.v2.ProductDto> toListProductDtoV2(List<Product> products);
}