package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.CartDto;
import com.mavic.storeapi.dtos.CartItemDto;
import com.mavic.storeapi.dtos.NewCartItemDto;
import com.mavic.storeapi.entities.Cart;
import com.mavic.storeapi.entities.CartItem;
import com.mavic.storeapi.mappers.CartMapper;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
    UriComponentsBuilder uriComponentsBuilder
    ){
        var cart = new Cart();
        cartRepository.save(cart);

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).body(cartMapper.toDto(cart));
    }

    @PostMapping("/{cartsId}/items")
    public ResponseEntity<CartItemDto> addProductToCart(
            @PathVariable(name = "cartsId") UUID id,
            @RequestBody NewCartItemDto newCartItemDto
            ){
        if(!cartRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        if(!productRepository.existsById(newCartItemDto.getProductId())){
            return ResponseEntity.badRequest().build();
        }
        var cart = cartRepository.findCartById(id);
        var cartItem = cart.getCartItems().stream()
                .filter(cartContent -> cartContent.getProduct().getId().equals(newCartItemDto.getProductId()))
                .findFirst()
                .orElse(null);
        if(cartItem == null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            cartItem = new CartItem();
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItem.setProduct(productRepository.findById(newCartItemDto.getProductId()).get());
            cartItem.setCart(cart);
        }


    }
}
