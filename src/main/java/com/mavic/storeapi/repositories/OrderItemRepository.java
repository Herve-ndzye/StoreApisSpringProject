package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}