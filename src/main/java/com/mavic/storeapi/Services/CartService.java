package com.mavic.storeapi.Services;

import com.mavic.storeapi.dtos.NewCartItemDto;
import com.mavic.storeapi.entities.Cart;
import com.mavic.storeapi.entities.CartItem;
import com.mavic.storeapi.entities.Product;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.ProductRepository;
import jakarta.validation.constraints.NotNull;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public Cart createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cart;
    }

    public Cart getCartById(UUID cartId){
        return cartRepository.getCartWithItems(cartId).orElse(null);
    }

    public Product getProduct(NewCartItemDto product){
        return productRepository.findById(product.getProductId()).orElse(null);
    }

    public CartItem  addItemToCart(Cart cart, Product product){
       var cartItem =  cart.addItem(product);
        cartRepository.save(cart);
        return cartItem;
    }

    public CartItem getCartItemById(Cart cart,Long productId){
        return cart.getItem(productId);
    }

    public CartItem changeItemQuantity(Cart cart,CartItem cartItem,Integer quantity){
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartItem;
    }
}
