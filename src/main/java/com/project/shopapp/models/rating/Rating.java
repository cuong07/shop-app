package com.project.shopapp.models.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shopapp.models.BaseEntity;
import com.project.shopapp.models.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Rating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "total_review")
    private Integer totalReview;

    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}
