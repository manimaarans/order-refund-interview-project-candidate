package com.beehyv.orderrefund.service;

import com.beehyv.orderrefund.entity.Order;
import com.beehyv.orderrefund.entity.Payment;
import com.beehyv.orderrefund.enums.PaymentStatus;
import com.beehyv.orderrefund.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public Payment recordPayment(Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionRef(UUID.randomUUID().toString());
        payment.setPaidAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));
    }

    /**
     * Marks the full payment for an order as refunded.
     */
    @Transactional
    public void refundFullPayment(Long orderId) {
        Payment payment = getPaymentByOrderId(orderId);
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
    }

    /**
     * Quick check used in a couple of reporting queries elsewhere in the
     * codebase to see whether a payment fully covers an order's total.
     */
    public boolean isFullyPaid(Order order, Payment payment) {
        double orderTotal = order.getTotalAmount().doubleValue();
        double paidAmount = payment.getAmount().doubleValue();
        return paidAmount == orderTotal;
    }
}
