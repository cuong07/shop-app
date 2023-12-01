package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Min(value = 1, message = "User's ID must be > 0")
    @JsonProperty("user_id")
    private  Long userId;
    @JsonProperty("fullname")
    private String fullName;

    private String email;
    private String address;
    private String note;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    @Min(value = 5, message = "Phone number must be at least 5 character")
    private String phoneNumber;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
