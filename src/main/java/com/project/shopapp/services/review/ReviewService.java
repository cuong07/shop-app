package com.project.shopapp.services.review;

import com.project.shopapp.dtos.ReviewDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.review.Review;
import com.project.shopapp.models.user.User;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.ReviewRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.rating.IRatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final IRatingService ratingService;

    @Override
    public Page<Review> getListReviewByProduct(Long productId, PageRequest pageRequest) throws Exception {
        Page<Review> reviews = reviewRepository.findAllByProductId(productId, pageRequest);
        return reviews;
    }

    @Override
    public Review createReview(ReviewDTO reviewDTO) throws Exception {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Optional<Review> optionalReview = reviewRepository.findByUserIdAndProductId(reviewDTO.getUserId(), reviewDTO.getProductId());

        if (!optionalReview.isEmpty()) {
            throw new Exception("Limit review for product");
        }
        // update rating
        ratingService.updateOrCreateRating(reviewDTO.getRating(), reviewDTO.getProductId());

        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .isEdited(false)
                .user(user)
                .product(product)
                .build();
        review.setCreatedAt(reviewDTO.getCreatedAt());
        review.setUpdatedAt(reviewDTO.getUpdatedAt());
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, ReviewDTO reviewDTO) throws Exception {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            throw new DataNotFoundException("Review not found");
        }
        //        ratingService.updateOrCreateRating(reviewDTO.getRating(), reviewDTO.getProduct().getId());
        Review review = optionalReview.get();
        review.setContent(reviewDTO.getContent());
        review.setIsEdited(true);
        review.setRating(review.getRating());
        review.setUpdatedAt(reviewDTO.getUpdatedAt());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) throws Exception {
        reviewRepository.deleteById(id);
    }
}
