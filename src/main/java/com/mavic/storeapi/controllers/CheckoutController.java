package com.mavic.storeapi.controllers;

import com.mavic.storeapi.Services.AuthService;
import com.mavic.storeapi.Services.CartService;
import com.mavic.storeapi.dtos.CheckoutRequest;
import com.mavic.storeapi.dtos.CheckoutResponse;
import com.mavic.storeapi.entities.Order;
import com.mavic.storeapi.entities.OrderItem;
import com.mavic.storeapi.entities.OrderStatus;
import com.mavic.storeapi.repositories.CartRepository;
import com.mavic.storeapi.repositories.OrderItemRepository;
import com.mavic.storeapi.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authservice;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;

    @PostMapping
    public ResponseEntity<?> checkout(
          @Valid @RequestBody CheckoutRequest request
    ) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("Error","Cart not found.")
            );
        }
        else if(cart.getCartItems().isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("Error","Cart is empty.")
            );
        }
        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authservice.getCurrentUser());

        cart.getCartItems().forEach(cartItem -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
        });
        orderRepository.save(order);
        cartService.clearCart(cart);

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
