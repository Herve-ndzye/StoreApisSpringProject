package com.mavic.storeapi.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemDto {
    private CartProductDto products;
    private Integer quantity;
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
