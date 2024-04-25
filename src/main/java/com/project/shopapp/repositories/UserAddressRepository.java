package com.project.shopapp.repositories;

import com.project.shopapp.models.product.ProductImage;
import com.project.shopapp.models.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long > {
    List<UserAddress> findByUserId(Long userId);

}
