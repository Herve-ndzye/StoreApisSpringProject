package com.mavic.storeapi.repositories;

import com.mavic.storeapi.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}