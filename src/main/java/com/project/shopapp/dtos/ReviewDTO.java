package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.BaseEntity;
import com.project.shopapp.models.product.Product;
import com.project.shopapp.models.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO extends BaseEntity {
    @JsonProperty("content")
    private String content;

    @JsonProperty("rating")
    @Min(value = 1, message = "Rating  must be >= 1")
    @Max(value = 5, message = "Rating  must be <= 5")
    private int rating;

    private Boolean isEdited;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("product_id")
    private Long productId;

}
