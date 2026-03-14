package com.mavic.storeapi.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id",target = "categoryId")
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    void updateEntity(Product product,@MappingTarget ProductDto request);
}
