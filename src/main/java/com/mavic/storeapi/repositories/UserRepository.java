package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
