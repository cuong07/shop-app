package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse extends BaseResponse {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    private String status;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private Date shippingDate;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("active")
    private Boolean active;
}
