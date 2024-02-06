package com.project.shopapp.services.review;

import com.project.shopapp.dtos.ReviewDTO;
import com.project.shopapp.models.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IReviewService {
    Page<Review> getListReviewByProduct(Long productId, PageRequest pageRequest) throws Exception;
    Review createReview(ReviewDTO reviewDTO) throws Exception;
    Review updateReview(Long id, ReviewDTO reviewDTO) throws Exception;
    void deleteReview(Long id) throws Exception;
}
