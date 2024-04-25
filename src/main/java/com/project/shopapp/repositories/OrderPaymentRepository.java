package com.project.shopapp.repositories;

import com.project.shopapp.models.cart.Cart;
import com.project.shopapp.models.payment.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
    OrderPayment findFirstByUserIdAndStatusAndIsActiveOrderByCreatedAtDesc(Long userId, boolean active, boolean status);
}
