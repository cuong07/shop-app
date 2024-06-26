package com.project.shopapp.models.cart;

import com.project.shopapp.models.BaseEntity;
import com.project.shopapp.models.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "number_of_products", nullable = false)
    private int numberOfProducts;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "total_money", nullable = false)
    private Float totalMoney;

    @Column(name = "color")
    private String color;

}
