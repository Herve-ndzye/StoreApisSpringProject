package com.mavic.storeapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts", schema = "store_api")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created" , insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
         for (CartItem cartItem : cartItems) {
             totalPrice = totalPrice.add(cartItem.getTotalPrice());
         }
         return totalPrice;
    }

}