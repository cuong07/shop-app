package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressDTO {

    @JsonProperty("address_one")
    @Size(min =  5, max = 200, message = "Please type correct address")
    private String addressOne;

    @JsonProperty("address_second")
    private String addressSecond;

    @JsonProperty("city")
    private String city;

    @JsonProperty("province")
    private String province;

    @JsonProperty("country")
    private String country;
}
