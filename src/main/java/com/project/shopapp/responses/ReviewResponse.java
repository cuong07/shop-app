package com.project.shopapp.responses;

import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.review.Review;
import com.project.shopapp.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReviewResponse extends BaseResponse {
    private Long id;
    private String content;
    private int rating;
    private Boolean isEdited;
    private UserResponse user;
    public static ReviewResponse fromReviewResponse(Review review) {
        ReviewResponse reviewResponse = ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .isEdited(review.getIsEdited())
                .user(UserResponse.fromUser(review.getUser()))
                .rating(review.getRating())
                .build();
        reviewResponse.setCreatedAt(review.getCreatedAt());
        reviewResponse.setUpdatedAt(review.getUpdatedAt());
        return reviewResponse;
    }
}
