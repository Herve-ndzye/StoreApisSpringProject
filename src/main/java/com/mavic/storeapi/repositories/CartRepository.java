package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @EntityGraph(attributePaths = "cartItems.product")
    @Query("SELECT c From Cart c Where c.id = :cartId")
    Optional<Cart> getCartWithItems(@Param("cartId") UUID id);
}