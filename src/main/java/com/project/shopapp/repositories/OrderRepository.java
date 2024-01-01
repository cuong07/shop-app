package com.project.shopapp.repositories;

import com.project.shopapp.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // findBy + field
    List<Order> findByUserId(Long id);
}
