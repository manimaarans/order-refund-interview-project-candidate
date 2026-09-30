package com.beehyv.orderrefund.controller;

import com.beehyv.orderrefund.entity.Order;
import com.beehyv.orderrefund.entity.Payment;
import com.beehyv.orderrefund.enums.OrderStatus;
import com.beehyv.orderrefund.dto.PaymentResponse;
import com.beehyv.orderrefund.service.OrderService;
import com.beehyv.orderrefund.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentForOrder(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(toResponse(payment));
    }

    @PostMapping("/order/{orderId}/pay")
    public ResponseEntity<PaymentResponse> payForOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        if (order.getStatus() != OrderStatus.PLACED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Payment payment = paymentService.recordPayment(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(payment));
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .transactionRef(payment.getTransactionRef())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
