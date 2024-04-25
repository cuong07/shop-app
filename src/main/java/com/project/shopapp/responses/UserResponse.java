package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.user.Role;
import com.project.shopapp.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse extends BaseResponse {
//    @JsonProperty("id")
    private Long id;
//    @JsonProperty("full_name")
    private String fullName;
//    @JsonProperty("phone_number")
    private String phoneNumber;
//    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
//    @JsonProperty("facebook_account_id")
    private Long facebookAccountId;
//    @JsonProperty("google_account_id")
    private Long googleAccountId;
//    @JsonProperty("role")
    private Role role;
//    @JsonProperty("user_addresses")
    private List<UserAddressResponse> userAddresses;
    public static UserResponse fromUser(User user) {
         UserResponse userResponse =  UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .role(user.getRole())
                .userAddresses(user.getUserAddresses().stream().map(UserAddressResponse::fromUserAddress).toList())
                .build();

         userResponse.setCreatedAt(user.getCreatedAt());
         userResponse.setUpdatedAt(user.getUpdatedAt());
         return  userResponse;
    }

}
