package com.beehyv.orderrefund.service;

import com.beehyv.orderrefund.dto.CreateOrderRequest;
import com.beehyv.orderrefund.dto.OrderItemRequest;
import com.beehyv.orderrefund.entity.Customer;
import com.beehyv.orderrefund.entity.Order;
import com.beehyv.orderrefund.enums.OrderStatus;
import com.beehyv.orderrefund.exception.OrderNotFoundException;
import com.beehyv.orderrefund.repository.CustomerRepository;
import com.beehyv.orderrefund.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Asha Rao");
        customer.setEmail("asha.rao@example.com");
    }

    @Test
    void createOrder_calculatesTotalAmountFromItems() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderItemRequest itemRequest = new OrderItemRequest("Mouse", 2, BigDecimal.valueOf(500));
        CreateOrderRequest request = new CreateOrderRequest(1L, List.of(itemRequest));

        Order result = orderService.createOrder(request);

        assertEquals(BigDecimal.valueOf(1000), result.getTotalAmount());
        assertEquals(OrderStatus.PLACED, result.getStatus());
    }

    @Test
    void getOrder_throwsWhenOrderMissing() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(99L));
    }

    @Test
    void cancelOrder_setsStatusToCancelledAndTriggersRefund() {
        Order order = new Order();
        order.setId(5L);
        order.setStatus(OrderStatus.PLACED);
        when(orderRepository.findById(5L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.cancelOrder(5L);

        assertEquals(OrderStatus.CANCELLED, result.getStatus());
        verify(paymentService, times(1)).refundFullPayment(5L);
    }
}
