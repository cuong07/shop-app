package com.project.shopapp.repositories;

import com.project.shopapp.models.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
//    List<CartDetail> findByCartId(Long cartId);
}
