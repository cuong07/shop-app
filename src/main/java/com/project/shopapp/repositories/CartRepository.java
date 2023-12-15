package com.project.shopapp.repositories;

import com.project.shopapp.models.Cart;
import com.project.shopapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartDetails WHERE c.id = :id")
    Optional<Cart> getDetailCart(@Param("id") Long id);
}
