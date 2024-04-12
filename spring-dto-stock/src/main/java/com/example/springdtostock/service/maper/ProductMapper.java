package com.example.springdtostock.service.maper;

import com.example.springdtostock.dto.ProductDto;
import com.example.springdtostock.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", expression = "java(new Category(productDto.getCategory()))")
    Product toEntity(ProductDto productDto);
    @Mapping(target = "category", source = "product.category.name")
    ProductDto toDto(Product product);
    @Mapping(target = "category", ignore = true)
    List<ProductDto> toDtoList(List<Product> products);
    @Mapping(target = "category", ignore = true)
    void update(@MappingTarget Product entity, ProductDto productDto);
}