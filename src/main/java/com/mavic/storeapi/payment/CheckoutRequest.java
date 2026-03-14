package com.mavic.storeapi.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequest {
    @NotNull(message = "The Cart Id is required.")
    private UUID cartId;
}
