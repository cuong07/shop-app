package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        Order order = new Order();
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = " + orderDTO.getUserId()));

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);

        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = order.getShippingDate() == null ? LocalDate.now() : order.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at last today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) throws Exception {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id = " + id));
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id = " + id));
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find user with id = " + orderDTO.getUserId()));
        if (existingOrder != null) {
            modelMapper.typeMap(OrderDTO.class, Order.class)
                    .addMappings(mapper -> mapper.skip(Order::setId));
            modelMapper.map(orderDTO, existingOrder);
            existingOrder.setUser(user);
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) throws Exception {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id = " + id));
        existingOrder.setActive(false);
        orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
