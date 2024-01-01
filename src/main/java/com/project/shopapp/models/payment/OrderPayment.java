package com.project.shopapp.models.payment;

import com.project.shopapp.models.BaseEntity;
import com.project.shopapp.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "note")
    private String note;

    @Column(name = "total")
    private Float total;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "message", length = 200)
    private String message;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_active")
    private Boolean isActive;

}
