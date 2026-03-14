package com.mavic.storeapi.payment;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
