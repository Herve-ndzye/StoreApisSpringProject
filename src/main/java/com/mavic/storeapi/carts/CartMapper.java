package com.mavic.storeapi.carts;

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
