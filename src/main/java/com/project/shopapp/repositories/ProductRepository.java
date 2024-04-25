package com.project.shopapp.repositories;

import com.project.shopapp.models.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);


    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :id")
    Optional<Product> getDetailProduct(@Param("id") Long id);
}
//@Query("SELECT p FROM Product p WHERE " +
//        "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
//        "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
//        "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
//        "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
//        "AND (:availability IS NULL OR p.isAvailable = :availability) " +
//        "AND (:brandId IS NULL OR :brandId = 0 OR p.brand.id = :brandId)")
//List<Product> searchProducts(
//        @Param("categoryId") Long categoryId,
//        @Param("keyword") String keyword,
//        @Param("minPrice") BigDecimal minPrice,
//        @Param("maxPrice") BigDecimal maxPrice,
//        @Param("availability") Boolean availability,
//        @Param("brandId") Long brandId);