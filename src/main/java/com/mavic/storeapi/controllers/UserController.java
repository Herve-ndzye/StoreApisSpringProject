package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.UserDto;
import com.mavic.storeapi.dtos.UserRegisterRequest;
import com.mavic.storeapi.mappers.UserMapper;
import com.mavic.storeapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers( @RequestParam(required = false,defaultValue = "",name = "sort") String sort) {
        if(!Set.of("name", "email").contains(sort))
            sort = "name";
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(user -> userMapper.toDto(user))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user =  userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserRegisterRequest user,
            UriComponentsBuilder uriBuilder
    ){
        var newUser = userMapper.toEntity(user);
        userRepository.save(newUser);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.toDto(newUser));
    }
}
