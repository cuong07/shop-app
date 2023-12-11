package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.CartDetail;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailResponse extends BaseResponse {
    private Long id;

    @JsonProperty("cart_id")
    private Long cartId;

    @JsonProperty("product")
    private ProductResponse product;

    @JsonProperty("product_of_number")
    private int productOfNumber;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("color")
    private String color;

    public static CartDetailResponse fromCartDetail(CartDetail cartDetail) {
        CartDetailResponse cartDetailResponse =  CartDetailResponse.builder()
                .id(cartDetail.getId())
                .price(cartDetail.getPrice())
                .productOfNumber(cartDetail.getProductOfNumber())
                .totalMoney(cartDetail.getTotalMoney())
                .color(cartDetail.getColor())
                .product(ProductResponse.fromProduct(cartDetail.getProduct()))
                .cartId(cartDetail.getCart().getId())
                .build();
        cartDetailResponse.setCreatedAt(cartDetail.getCreatedAt());
        cartDetailResponse.setUpdatedAt(cartDetail.getUpdatedAt());
        return cartDetailResponse;
    }
}
