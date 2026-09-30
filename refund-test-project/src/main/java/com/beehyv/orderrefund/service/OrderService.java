package com.beehyv.orderrefund.service;

import com.beehyv.orderrefund.dto.CreateOrderRequest;
import com.beehyv.orderrefund.dto.OrderItemRequest;
import com.beehyv.orderrefund.entity.Customer;
import com.beehyv.orderrefund.entity.Order;
import com.beehyv.orderrefund.entity.OrderItem;
import com.beehyv.orderrefund.enums.OrderStatus;
import com.beehyv.orderrefund.exception.OrderNotFoundException;
import com.beehyv.orderrefund.repository.CustomerRepository;
import com.beehyv.orderrefund.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles order lifecycle: creation, retrieval, cancellation.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository,
                         CustomerRepository customerRepository,
                         PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new OrderNotFoundException(request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductName(itemRequest.getProductName());
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());
            order.addItem(item);
            total = total.add(itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    /**
     * Cancels an order and triggers a full refund of the associated payment.
     */
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getOrder(orderId);

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        paymentService.refundFullPayment(orderId);

        return order;
    }
}
