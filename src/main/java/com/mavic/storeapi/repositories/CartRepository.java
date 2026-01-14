package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findCartById(UUID id);
}