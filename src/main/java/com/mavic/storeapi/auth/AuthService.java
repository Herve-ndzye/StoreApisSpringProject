package com.mavic.storeapi.auth;

import com.mavic.storeapi.users.User;
import com.mavic.storeapi.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepository;

    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        var userId = (Long)authentication.getPrincipal();

        return userRepository.getUserById(userId).orElse(null);
    }
}
