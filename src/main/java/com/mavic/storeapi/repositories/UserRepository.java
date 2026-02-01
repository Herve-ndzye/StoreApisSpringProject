package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.lang.ScopedValue;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);
}
