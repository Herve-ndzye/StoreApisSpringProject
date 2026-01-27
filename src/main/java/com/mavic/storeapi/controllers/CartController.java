package com.mavic.storeapi.controllers;

import com.mavic.storeapi.Services.CartService;
import com.mavic.storeapi.dtos.CartDto;
import com.mavic.storeapi.dtos.CartItemDto;
import com.mavic.storeapi.dtos.NewCartItemDto;
import com.mavic.storeapi.dtos.UpdateCartItemRequest;
import com.mavic.storeapi.entities.Cart;
import com.mavic.storeapi.entities.CartItem;
import com.mavic.storeapi.exceptions.CartNotFoundException;
import com.mavic.storeapi.exceptions.ProductNotFoundException;
import com.mavic.storeapi.mappers.CartMapper;
import com.mavic.storeapi.mappers.ProductMapper;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Carts")
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
    public ResponseEntity<CartItemDto> addItemToCart(
            @Parameter(description = "Id of the cart")
            @PathVariable UUID cartId,
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
        cartService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart"));
    }

}
