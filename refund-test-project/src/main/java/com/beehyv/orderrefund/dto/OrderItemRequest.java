package com.beehyv.orderrefund.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @NotBlank(message = "productName is required")
    private String productName;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

    @NotNull(message = "unitPrice is required")
    private BigDecimal unitPrice;
}
