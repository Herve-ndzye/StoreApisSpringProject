package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
