package com.mavic.storeapi.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "Quantity can not be null")
    @Min(value = 1,message = "Quantity must be greater than 0")
    @Max(value = 100,message = "Quantity must be less than 100")
    private Integer quantity;
}
