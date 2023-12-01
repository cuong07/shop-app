package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id) throws Exception;
    Order updateOrder(Long id, OrderDTO orderDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;
    List<Order> findByUserId(Long userId);
}
