package com.example.springdocker.mapper;

import com.example.springdocker.dto.ProductDto;
import com.example.springdocker.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    List<ProductDto> toListDto(List<Product> products);

}
