package com.project.shopapp.services.rating;

import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.rating.Rating;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RatingService implements IRatingService {
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;
    @Override
    public Rating updateOrCreateRating(Integer rating, Long productId) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Rating existRating = ratingRepository.findByProductId(productId);
        if (existRating != null) {
            Double avgRating = existRating.getRating() / existRating.getTotalReview() + 1;
            existRating.setRating(avgRating);
            existRating.setTotalReview(existRating.getTotalReview() + 1);
            return ratingRepository.save(existRating);
        }
        Rating newRating = new Rating();
        newRating.setProduct(product);
        newRating.setTotalReview(1);
        newRating.setRating(Double.parseDouble(String.valueOf(rating)));
        newRating.setCreatedAt(LocalDateTime.now());
        newRating.setUpdatedAt(LocalDateTime.now());
        return ratingRepository.save(newRating);
    }

}
