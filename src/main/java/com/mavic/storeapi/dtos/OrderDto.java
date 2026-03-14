package com.mavic.storeapi.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mavic.storeapi.entities.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({ "id", "status", "createdAt", "items", "totalPrice" })
@Data
public class OrderDto {
    private Long id;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

}
