package com.mavic.storeapi.payment;

import com.mavic.storeapi.dtos.ErrorDto;
import com.mavic.storeapi.exceptions.CartEmptyException;
import com.mavic.storeapi.exceptions.CartNotFoundException;
import com.mavic.storeapi.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;


    @PostMapping
    public ResponseEntity<?> checkout(
          @Valid @RequestBody CheckoutRequest request
    ) {
            return ResponseEntity.ok(checkoutService.checkout(request));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
           @RequestHeader Map<String, String> headers,
           @RequestBody String payload
    ){
        checkoutService.handleWebhookEvent(new WebhookRequest(headers,payload));
        return ResponseEntity.ok().build();
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
