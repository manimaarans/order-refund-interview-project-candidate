package com.beehyv.orderrefund.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotEmpty(message = "order must contain at least one item")
    @Valid
    private List<OrderItemRequest> items;
}
