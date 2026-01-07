package com.mavic.storeapi.mappers;

import com.mavic.storeapi.dtos.UserDto;
import com.mavic.storeapi.dtos.UserRegisterRequest;
import com.mavic.storeapi.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserRegisterRequest user);
}
