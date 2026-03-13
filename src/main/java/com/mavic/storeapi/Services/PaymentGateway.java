package com.mavic.storeapi.Services;

import com.mavic.storeapi.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
