package com.mavic.storeapi.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartItemDto {
    private List<ProductDto> products = new ArrayList<>();
    private Integer quantity;
    private BigDecimal price = BigDecimal.ZERO;

}
