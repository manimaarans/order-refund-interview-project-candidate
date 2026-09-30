package com.beehyv.orderrefund.repository;

import com.beehyv.orderrefund.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
