package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}