package com.beehyv.orderrefund.repository;

import com.beehyv.orderrefund.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
