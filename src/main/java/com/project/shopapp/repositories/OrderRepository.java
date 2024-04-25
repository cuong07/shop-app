package com.project.shopapp.repositories;

import com.project.shopapp.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // findBy + field
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderDetails WHERE o.user.id = :id")
    List<Order> findByUserId(Long id);
}
