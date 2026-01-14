package com.mavic.storeapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewCartItemDto {

    @NotNull(message = "Product Id can not be null")
    private Long productId;
}
