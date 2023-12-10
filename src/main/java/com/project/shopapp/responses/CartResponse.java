package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Cart;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse extends BaseResponse {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("note")
    private String note;

    public static CartResponse fromCart(Cart cart) {
        CartResponse cartResponse = CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .note(cart.getNote())
                .build();
        cartResponse.setCreatedAt(cart.getCreatedAt());
        cartResponse.setUpdatedAt(cart.getUpdatedAt());
        return cartResponse;
    }
}
