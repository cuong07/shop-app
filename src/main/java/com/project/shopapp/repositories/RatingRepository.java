package com.project.shopapp.repositories;

import com.project.shopapp.models.rating.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByProductId(Long productId);
}
