package com.mavic.storeapi.controllers;

import com.mavic.storeapi.Services.CheckoutService;
import com.mavic.storeapi.dtos.CheckoutRequest;
import com.mavic.storeapi.dtos.CheckoutResponse;
import com.mavic.storeapi.dtos.ErrorDto;
import com.mavic.storeapi.exceptions.CartEmptyException;
import com.mavic.storeapi.exceptions.CartNotFoundException;
import com.mavic.storeapi.exceptions.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Value("${stripe.webhookSecretKey}")
    private String webhook;

    @PostMapping
    public ResponseEntity<?> checkout(
          @Valid @RequestBody CheckoutRequest request
    ) {
            return ResponseEntity.ok(checkoutService.checkout(request));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
           @RequestHeader("Stripe-Signature") String signature,
           @RequestBody String payload
    ){
        try {
            var event = Webhook.constructEvent(payload,signature,webhook);
            System.out.println(event.getType());

            switch (event.getType()) {
                case "payment_intent.succeeded"->{

                }
                case "payment_intent.failed"->{

                }
            }
            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
