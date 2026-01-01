package com.mavic.storeapi.repositories;


import com.mavic.storeapi.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}