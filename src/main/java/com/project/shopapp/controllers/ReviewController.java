package com.project.shopapp.controllers;


import com.project.shopapp.dtos.ReviewDTO;
import com.project.shopapp.models.review.Review;
import com.project.shopapp.responses.ListResponse;
import com.project.shopapp.responses.ReviewResponse;
import com.project.shopapp.services.review.IReviewService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/reviews")
@AllArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> create(@RequestBody ReviewDTO reviewDTO) {
        try {
            Review review = reviewService.createReview(reviewDTO);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> udpate(@RequestPart(name = "id") Long id, @RequestBody ReviewDTO reviewDTO) {
        try {
            Review review = reviewService.updateReview(id, reviewDTO);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getReviews(
            @PathVariable(name = "id") Long productId,
            @RequestParam(name = "limit", defaultValue = "0") int limit,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        try {
            PageRequest pageRequest = PageRequest.of(
                    page,
                    limit,
                    Sort.by("id").ascending());
            Page<Review> reviewPage = reviewService.getListReviewByProduct(productId, pageRequest);
            Page<ReviewResponse> reviewResponsePage = reviewPage.map(ReviewResponse::fromReviewResponse);
            return  ResponseEntity.ok(reviewResponsePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
