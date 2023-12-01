package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id) throws Exception;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception;
    void deleteOrderDetail(Long id) throws Exception;
    List<OrderDetail> getOrderDetails(Long id);
}
