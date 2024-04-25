package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.order.OrderDetail;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDetailResponse {
    private Long id;

//    @JsonProperty("order_id")
    private Long orderId;

//    @JsonProperty("product_id")
    private Long productId;

//    @JsonProperty("price")
    private Float price;

//    @JsonProperty("number_of_products")
    private Integer numberOfProducts;

//    @JsonProperty("total_money")
    private Float totalMoney;

//    @JsonProperty("color")
    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .orderId(orderDetail.getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .id(orderDetail.getId())
                .build();
    }

}
