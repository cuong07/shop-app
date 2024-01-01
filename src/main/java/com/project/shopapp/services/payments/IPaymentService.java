package com.project.shopapp.services.payments;

import com.project.shopapp.dtos.OrderPaymentDTO;
import com.project.shopapp.models.payment.OrderPayment;

public interface IPaymentService {
    OrderPayment createOrderPayment(OrderPaymentDTO orderPaymentDTO) throws Exception;
    OrderPayment updateOrderPayment(Long id, OrderPaymentDTO orderPaymentDTO) throws Exception;

    void deleteOrderPayment(Long id) throws Exception;
}
