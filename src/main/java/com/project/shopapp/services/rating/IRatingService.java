package com.project.shopapp.services.rating;

import com.project.shopapp.models.rating.Rating;

public interface IRatingService {
    Rating updateOrCreateRating(Integer rating, Long productId) throws Exception;
}
