package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    @JsonProperty("user_id")
    private Long userId;

    private String note;

    @JsonProperty("is_active")
    private Boolean isActive;


}
