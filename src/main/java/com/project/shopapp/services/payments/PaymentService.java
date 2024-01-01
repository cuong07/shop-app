package com.project.shopapp.services.payments;

import com.project.shopapp.dtos.OrderPaymentDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.payment.OrderPayment;
import com.project.shopapp.models.user.User;
import com.project.shopapp.repositories.OrderPaymentRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService implements  IPaymentService {
    private final OrderPaymentRepository orderPaymentRepository;
    private final UserRepository userRepository;
    @Override
    public OrderPayment createOrderPayment(OrderPaymentDTO orderPaymentDTO) throws Exception {
        User user = userRepository.findById(orderPaymentDTO.getUser().getId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        OrderPayment orderPayment = OrderPayment.builder()
                .paymentTime(orderPaymentDTO.getPaymentTime())
                .total(orderPaymentDTO.getTotal())
                .note(orderPaymentDTO.getNote())
                .message(orderPaymentDTO.getMessage())
                .status(orderPaymentDTO.getStatus())
                .user(orderPaymentDTO.getUser())
                .isActive(orderPaymentDTO.getIsActive())
                .transactionId(orderPaymentDTO.getTransactionId())
                .build();
        return  orderPaymentRepository.save(orderPayment);
    }

    @Override
    public OrderPayment updateOrderPayment(Long id, OrderPaymentDTO orderPaymentDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteOrderPayment(Long id) throws Exception {
        OrderPayment orderPayment = orderPaymentRepository.findFirstByUserIdAndStatusAndIsActiveOrderByCreatedAtDesc(id, true, true);
        orderPayment.setIsActive(false);
        orderPaymentRepository.save(orderPayment);
    }
}
