package com.project.shopapp.dtos;

import com.project.shopapp.models.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentDTO {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String note;

    @NotBlank(message = "Total is required")
    private Float total;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @NotBlank(message = "Transaction Id is required")
    private String transactionId;

    private String message;

    private Boolean status;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;
}
