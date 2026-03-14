package com.mavic.storeapi.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserRegisterRequest user);
    void updateEntity(UserUpdateDto userDto, @MappingTarget User user); 
}
