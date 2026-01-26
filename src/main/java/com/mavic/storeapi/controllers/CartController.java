package com.mavic.storeapi.controllers;

import com.mavic.storeapi.Services.CartService;
import com.mavic.storeapi.dtos.CartDto;
import com.mavic.storeapi.dtos.CartItemDto;
import com.mavic.storeapi.dtos.NewCartItemDto;
import com.mavic.storeapi.dtos.UpdateCartItemRequest;
import com.mavic.storeapi.entities.Cart;
import com.mavic.storeapi.entities.CartItem;
import com.mavic.storeapi.mappers.CartMapper;
import com.mavic.storeapi.mappers.ProductMapper;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
    UriComponentsBuilder uriComponentsBuilder
    ){
        var cart = cartService.createCart();

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).body(cartMapper.toDto(cart));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable UUID cartId,
                                                     @RequestBody NewCartItemDto product){

        var cartItem = cartService.addItemToCart(product,cartId);
        var response = cartMapper.toCartItemDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(
            @PathVariable UUID cartId
    ){
       var cart = cartService.getcart(cartId);
        var response = cartMapper.toDto(cart);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable(name = "cartId") UUID cartId
            ,@PathVariable(name = "productId") Long productId,
            @RequestBody UpdateCartItemRequest request
    ){
        var cartItem = cartService.updateCart(cartId,productId,request);

        return ResponseEntity.ok().body(cartMapper.toCartItemDto(cartItem));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable(name = "cartId") UUID cartId
            ,@PathVariable(name = "productId") Long productId
    ){
       cartService.deleteCartItem(cartId,productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(
            @PathVariable(name = "cartId") UUID cartId
    ){


        return ResponseEntity.noContent().build();
    }

}
