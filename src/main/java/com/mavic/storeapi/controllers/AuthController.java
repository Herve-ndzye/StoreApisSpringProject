package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.LoginDto;
import com.mavic.storeapi.exceptions.InvalidPasswordException;
import com.mavic.storeapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginDto request
    ){
        var user = userRepository.getUserByEmail(request.getEmail()).orElse(null);
        if(user == null){
            throw new InvalidPasswordException();
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException();
        }
        return ResponseEntity.ok().build();
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Void> handleInvalidPasswordException(InvalidPasswordException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
