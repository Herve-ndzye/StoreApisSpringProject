package com.mavic.storeapi.Services;

import com.mavic.storeapi.dtos.CheckoutRequest;
import com.mavic.storeapi.dtos.CheckoutResponse;
import com.mavic.storeapi.dtos.ErrorDto;
import com.mavic.storeapi.entities.Order;
import com.mavic.storeapi.exceptions.CartEmptyException;
import com.mavic.storeapi.exceptions.CartNotFoundException;
import com.mavic.storeapi.exceptions.PaymentException;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null) {
            throw new CartNotFoundException();
        }
        else if(cart.isEmpty()){
            throw new CartEmptyException();
        }
        var order = Order.fromCart(cart,authService.getCurrentUser());

        orderRepository.save(order);
        cartService.clearCart(cart);

        try{
            var checkoutSession = paymentGateway.createCheckoutSession(order);
            return new CheckoutResponse(order.getId(), checkoutSession.getCheckoutUrl());
        }catch(PaymentException e){
            orderRepository.delete(order);
            throw e;
        }
    }
}
