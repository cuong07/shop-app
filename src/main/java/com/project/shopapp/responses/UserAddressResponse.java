package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.user.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserAddressResponse {
//    @JsonProperty("id")
    private Long id;

//    @JsonProperty("address_one")
    private String addressOne;
//    @JsonProperty("address_second")
    private String addressSecond;
//    @JsonProperty("city")
    private String city;
//    @JsonProperty("province")
    private String province;
//    @JsonProperty("country")
    private String country;

    public static UserAddressResponse fromUserAddress(UserAddress userAddress){
        return  UserAddressResponse.builder()
                .id(userAddress.getId())
                .addressOne(userAddress.getAddressOne())
                .addressSecond(userAddress.getAddressSecond())
                .city(userAddress.getCity())
                .province(userAddress.getProvince())
                .country(userAddress.getCountry())
                .build();
    }
}
