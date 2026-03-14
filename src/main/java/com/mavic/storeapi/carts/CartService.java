package com.mavic.storeapi.carts;

import com.mavic.storeapi.products.Product;
import com.mavic.storeapi.products.ProductNotFoundException;
import com.mavic.storeapi.products.ProductRepository;
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
