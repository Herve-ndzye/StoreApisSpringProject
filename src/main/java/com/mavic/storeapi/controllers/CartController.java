package com.mavic.storeapi.controllers;

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

import java.math.BigDecimal;
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

    @PostMapping
    public ResponseEntity<CartDto> createCart(
    UriComponentsBuilder uriComponentsBuilder
    ){
        var cart = new Cart();
        cartRepository.save(cart);

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).body(cartMapper.toDto(cart));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable UUID cartId,
                                                     @RequestBody NewCartItemDto product){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        var productRequest = productRepository.findById(product.getProductId()).orElse(null);
        if(productRequest == null){
            return ResponseEntity.badRequest().build();
        }
        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productRequest.getId()))
                .findFirst()
                .orElse(null);
        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            cartItem = new CartItem();
            cartItem.setProduct(productRequest);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        var response = cartMapper.toCartItemDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(
            @PathVariable UUID cartId
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        var response = cartMapper.toDto(cart);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable(name = "cartId") UUID cartId
            ,@PathVariable(name = "productId") Long productId,
            @RequestBody UpdateCartItemRequest request
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("Error","Cart not Found")
            );
        }

        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if(cartItem == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("Error","Product was not Found in the cart")
            );
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok().body(cartMapper.toCartItemDto(cartItem));
    }


}
