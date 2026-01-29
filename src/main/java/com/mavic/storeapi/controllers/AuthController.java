package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.LoginDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginDto request
    ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(401).build();
    }

}
