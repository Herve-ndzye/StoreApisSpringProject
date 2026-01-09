package com.mavic.storeapi.mappers;

import com.mavic.storeapi.dtos.UserDto;
import com.mavic.storeapi.dtos.UserRegisterRequest;
import com.mavic.storeapi.dtos.UserUpdateDto;
import com.mavic.storeapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserRegisterRequest user);
    void updateEntity(UserUpdateDto userDto, @MappingTarget User user);
}
