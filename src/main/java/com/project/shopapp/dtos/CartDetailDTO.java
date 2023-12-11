package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
    @JsonProperty("cart_id")
    @Min(value = 1, message = "Order's ID must be > 0")
    private Long cartId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 0")
    private Long productId;

    @Min(value = 0, message = "Price must be > 0")
    private Float price;

    @Min(value = 1, message = "Number of product must be > 0")
    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be > 0")
    private Float totalMoney;

    private String color;
}
