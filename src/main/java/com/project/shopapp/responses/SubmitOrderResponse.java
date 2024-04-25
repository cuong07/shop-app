package com.project.shopapp.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitOrderResponse {
    private String message;
    private String url;
}
