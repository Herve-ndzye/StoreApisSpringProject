package com.mavic.storeapi.mappers;

import com.mavic.storeapi.dtos.OrderDto;
import com.mavic.storeapi.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
}
