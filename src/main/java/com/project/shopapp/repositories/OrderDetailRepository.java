package com.project.shopapp.repositories;

import com.project.shopapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // findBy + field
    List<OrderDetail> findByOrderId(Long id);
}
