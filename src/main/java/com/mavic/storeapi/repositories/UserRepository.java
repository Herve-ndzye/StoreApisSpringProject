package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    Optional<User> getUserByEmail(String email);
}
