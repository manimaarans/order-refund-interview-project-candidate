package com.beehyv.orderrefund.controller;

import com.beehyv.orderrefund.dto.CreateOrderRequest;
import com.beehyv.orderrefund.dto.OrderItemResponse;
import com.beehyv.orderrefund.dto.OrderResponse;
import com.beehyv.orderrefund.entity.Order;
import com.beehyv.orderrefund.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(toResponse(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<OrderResponse> responses = orderService.listOrders().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        return ResponseEntity.ok(toResponse(order));
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}
