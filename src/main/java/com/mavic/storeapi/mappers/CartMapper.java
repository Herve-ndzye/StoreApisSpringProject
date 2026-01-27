package com.mavic.storeapi.mappers;

import com.mavic.storeapi.dtos.CartDto;
import com.mavic.storeapi.dtos.CartItemDto;
import com.mavic.storeapi.dtos.CartProductDto;
import com.mavic.storeapi.entities.Cart;
import com.mavic.storeapi.entities.CartItem;
import com.mavic.storeapi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")
    CartItemDto toCartItemDto(CartItem cartItem);

    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    @Mapping(target = "items",source = "cartItems")
    CartDto toDto(Cart cart);

}
