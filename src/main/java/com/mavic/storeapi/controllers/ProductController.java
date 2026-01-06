package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.ProductDto;
import com.mavic.storeapi.entities.Product;
import com.mavic.storeapi.mappers.ProductMapper;
import com.mavic.storeapi.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private ProductMapper  productMapper;
    @GetMapping
    public List<ProductDto> fetchAllProducts(
            @RequestParam(name = "categoryId",
            defaultValue = "",
            required = false) Byte categoryId){
        if(categoryId == null){
            return productRepository.findAllWithCategory()
                    .stream()
                    .map(product -> productMapper.toDto(product))
                    .toList();
        }

            return  productRepository.findAllByCategoryId(categoryId)
                    .stream()
                    .map(product -> productMapper.toDto(product))
                    .toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> fetchProductById(@PathVariable Long id ){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));

    }
}
