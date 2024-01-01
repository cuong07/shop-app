package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.cart.Cart;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("cart_details")
    private List<CartDetailResponse> cartDetails = new ArrayList<>();
    public static CartResponse fromCart(Cart cart) {
        CartResponse cartResponse = CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .note(cart.getNote())
                .isActive(cart.getIsActive())
                .build();

        if (cart.getCartDetails() != null) {
            if (!cart.getCartDetails().isEmpty()) {
                cartResponse.setCartDetails(cart.getCartDetails().stream().map(CartDetailResponse::fromCartDetail).toList());
            } else {
                cartResponse.setCartDetails(List.of());
            }
        }
        cartResponse.setCreatedAt(cart.getCreatedAt());
        cartResponse.setUpdatedAt(cart.getUpdatedAt());
        return cartResponse;
    }
}
