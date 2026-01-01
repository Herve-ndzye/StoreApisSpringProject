package com.mavic.storeapi.repositories;


import com.mavic.storeapi.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}