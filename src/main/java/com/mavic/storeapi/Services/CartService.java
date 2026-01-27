package com.mavic.storeapi.Services;

import com.mavic.storeapi.dtos.NewCartItemDto;
import com.mavic.storeapi.dtos.UpdateCartItemRequest;
import com.mavic.storeapi.entities.Cart;
import com.mavic.storeapi.entities.CartItem;
import com.mavic.storeapi.entities.Product;
import com.mavic.storeapi.exceptions.CartItemNotFoundException;
import com.mavic.storeapi.exceptions.CartNotFoundException;
import com.mavic.storeapi.exceptions.ProductNotFoundException;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
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

    public CartItem  addItemToCart( NewCartItemDto product,UUID cartId){
        var cart = getCartById(cartId);
        if(cart == null){
            throw new CartNotFoundException();
        }
        var productRequest = getProduct(product);
        if(productRequest == null){
            throw new ProductNotFoundException();
        }
       var cartItem =  cart.addItem(productRequest);
        cartRepository.save(cart);
        return cartItem;
    }


    public CartItem changeItemQuantity(Cart cart,CartItem cartItem,Integer quantity){
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartItem;
    }

    public void removeItem(Long productId,Cart cart){
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(Cart cart){
        cart.clearCart();
        cartRepository.save(cart);
    }

    public Cart getcart(UUID cartId){
        var cart = getCartById(cartId);
        if(cart == null){
            throw new CartNotFoundException();
        }
        return cart;
    }

    public CartItem updateCart(UUID  cartId, Long productId, UpdateCartItemRequest request){
        var cart = getCartById(cartId);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var cartItem = cart.getItem(productId);
        if(cartItem == null){
            throw new CartItemNotFoundException();
        }
        cartItem = changeItemQuantity(cart,cartItem,request.getQuantity());

        return cartItem;
    }

    public void deleteCartItem(UUID cartId,Long productId){
        var cart = getCartById(cartId);
        if(cart == null){
            throw new CartNotFoundException();
        }
        removeItem(productId,cart);
    }

    public void clearCart(UUID cartId){
        var cart = getCartById(cartId);
        if(cart == null){
            throw new CartNotFoundException();
        }
        clearCart(cart);
    }
}
