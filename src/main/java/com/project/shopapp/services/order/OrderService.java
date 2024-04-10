package com.project.shopapp.services.order;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.order.Order;
import com.project.shopapp.models.order.OrderStatus;
import com.project.shopapp.models.payment.OrderPayment;
import com.project.shopapp.models.user.User;
import com.project.shopapp.models.user.UserAddress;
import com.project.shopapp.repositories.OrderPaymentRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.user.UserAddressService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserAddressService userAddressService;

    private final OrderPaymentRepository orderPaymentRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, UserAddressService userAddressService, OrderPaymentRepository orderPaymentRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userAddressService = userAddressService;
        this.orderPaymentRepository = orderPaymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        UserAddress userAddress = userAddressService.getUserAddressById(orderDTO.getAddressId());
        OrderPayment orderPayment = orderPaymentRepository.findFirstByUserIdAndStatusAndIsActiveOrderByCreatedAtDesc(user.getId(), true,true);
        if(!orderPayment.getStatus()){
            throw new Exception("Payment is failed!");
        }
        Order order = new Order();
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUserAddress(userAddress);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderPayment(orderPayment);
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
    public Order createOrderCOD(OrderDTO orderDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        UserAddress userAddress = userAddressService.getUserAddressById(orderDTO.getAddressId());
        Order order = new Order();
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUserAddress(userAddress);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderPayment(null);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

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
    public List<Order> findByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return orderRepository.findByUserId(user.getId());
    }

    @Override
    public List<Order> findByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return orderRepository.findByUserId(user.getId());
    }


    @Override
    public Boolean getPaymentStatus() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        OrderPayment orderPayment = orderPaymentRepository.findFirstByUserIdAndStatusAndIsActiveOrderByCreatedAtDesc(user.getId(), true, true);
        return  orderPayment.getStatus();
    }
}
