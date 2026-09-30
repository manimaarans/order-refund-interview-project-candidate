package com.beehyv.orderrefund.repository;

import com.beehyv.orderrefund.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
