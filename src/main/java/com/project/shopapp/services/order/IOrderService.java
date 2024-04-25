package com.project.shopapp.services.order;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.order.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order createOrderCOD(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id) throws Exception;
    Order updateOrder(Long id, OrderDTO orderDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;
    List<Order> findByUserId();
    List<Order> findByCurrentUser();
    Boolean getPaymentStatus() throws Exception;
}
