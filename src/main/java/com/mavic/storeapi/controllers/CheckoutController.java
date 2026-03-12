package com.mavic.storeapi.controllers;

import com.mavic.storeapi.Services.CheckoutService;
import com.mavic.storeapi.dtos.CheckoutRequest;
import com.mavic.storeapi.dtos.CheckoutResponse;
import com.mavic.storeapi.dtos.ErrorDto;
import com.mavic.storeapi.exceptions.CartEmptyException;
import com.mavic.storeapi.exceptions.CartNotFoundException;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(
          @Valid @RequestBody CheckoutRequest request
    ) {
        try{
            return ResponseEntity.ok(checkoutService.checkout(request));
        }catch (StripeException ex){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Error creating a checkout session"));
        }
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
