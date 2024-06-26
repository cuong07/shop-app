package com.project.shopapp.repositories;

import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    // check == != Null => Optional
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userAddresses WHERE u.id = :userId")
    Optional<User> getCurrentUser(@Param("userId") Long userId);

}
