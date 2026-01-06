package com.mavic.storeapi.mappers;

import com.mavic.storeapi.dtos.ProductDto;
import com.mavic.storeapi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id",target = "categoryId")
    ProductDto toDto(Product product);
}
