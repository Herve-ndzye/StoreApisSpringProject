package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.ProductDto;
import com.mavic.storeapi.entities.Product;
import com.mavic.storeapi.mappers.ProductMapper;
import com.mavic.storeapi.repositories.CategoryRepository;
import com.mavic.storeapi.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final CategoryRepository categoryRepository;
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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriComponentsBuilder

    ){
      var category = categoryRepository.findById( request.getCategoryId().byteValue()).orElse(null);
      if(category == null){
          return ResponseEntity.badRequest().build();
      }
      var product = productMapper.toEntity(request);
      product.setCategory(category);
      productRepository.save(product);
      request.setId(product.getId());
      var uri =  uriComponentsBuilder.path("/products").buildAndExpand(product.getId()).toUri();
      return  ResponseEntity.created(uri).body(productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@RequestBody ProductDto request){
        var category = categoryRepository.findById( request.getCategoryId().byteValue()).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().build();
        }
        var product =  productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productMapper.updateEntity(product,request);
        product.setCategory(category);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
