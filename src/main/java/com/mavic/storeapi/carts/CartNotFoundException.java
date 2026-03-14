package com.mavic.storeapi.carts;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(){
        super("Cart not found");
    }
}
