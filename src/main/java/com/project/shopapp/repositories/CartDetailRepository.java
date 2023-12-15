package com.project.shopapp.repositories;

import com.project.shopapp.models.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    Optional<CartDetail> findByCartIdAndProductId(Long cartId, Long productId);
//    CartDetail findByCartId(Long cartId);
}
