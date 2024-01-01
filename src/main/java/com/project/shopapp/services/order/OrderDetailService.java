package com.project.shopapp.services.order;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.order.Order;
import com.project.shopapp.models.order.OrderDetail;
import com.project.shopapp.models.product.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find order with id = " + orderDetailDTO.getOrderId()));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id = " + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailDTO.getPrice())
                .color(orderDetailDTO.getColor())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws Exception {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id = " + id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail existingOrderDetail = this.getOrderDetail(id);
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id = " + orderDetailDTO.getProductId()));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find order with id + " + orderDetailDTO.getOrderId()));
        if(existingOrderDetail != null){
            existingOrderDetail.setOrder(order);
            existingOrderDetail.setProduct(product);
            existingOrderDetail.setPrice(orderDetailDTO.getPrice());
            existingOrderDetail.setColor(orderDetailDTO.getColor());
            existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
            existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
            return orderDetailRepository.save(existingOrderDetail);
        }
        return null;
    }

    @Override
    public void deleteOrderDetail(Long id) throws Exception {
        OrderDetail existingOrderDetail = this.getOrderDetail(id);
        if(existingOrderDetail != null){
            orderDetailRepository.delete(existingOrderDetail);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }
}
