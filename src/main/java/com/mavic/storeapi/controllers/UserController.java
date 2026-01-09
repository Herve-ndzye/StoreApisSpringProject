package com.mavic.storeapi.controllers;

import com.mavic.storeapi.dtos.UserDto;
import com.mavic.storeapi.dtos.UserRegisterRequest;
import com.mavic.storeapi.dtos.UserUpdateDto;
import com.mavic.storeapi.dtos.ChangePasswordRequest;
import com.mavic.storeapi.mappers.UserMapper;
import com.mavic.storeapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id,@RequestBody UserUpdateDto request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return  ResponseEntity.notFound().build();
        }
        userMapper.updateEntity(request,user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest request
    ){
        var user =  userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        if(user.getPassword().equals(request.getOldPassword())){
            user.setPassword(request.getNewPassword());
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


    }
}
