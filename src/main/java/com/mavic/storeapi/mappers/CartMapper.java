package com.mavic.storeapi.mappers;

import com.mavic.storeapi.dtos.CartDto;
import com.mavic.storeapi.dtos.NewCartItemDto;
import com.mavic.storeapi.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toEntity(CartDto cartDto);
    CartDto toDto(Cart cart);
}
