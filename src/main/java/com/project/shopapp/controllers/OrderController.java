package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.cart.Cart;
import com.project.shopapp.models.cart.CartDetail;
import com.project.shopapp.models.order.Order;
import com.project.shopapp.services.cart.CartService;
import com.project.shopapp.services.order.OrderDetailService;
import com.project.shopapp.services.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final OrderDetailService orderDetailService;
    public OrderController(OrderService orderService, CartService cartService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cod")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> createOrderCOD(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Order order = orderService.createOrderCOD(orderDTO);
            Cart cart = cartService.getCartCurrentByUser();
            for (CartDetail cartDetail : cart.getCartDetails()) {
                OrderDetailDTO orderDetailDTO = getOrderDetailDTO(cartDetail, order);
                orderDetailService.createOrderDetail(orderDetailDTO);
            }
            cartService.deleteCart(cart.getId());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUserOrders() {
        try {
            List<Order> orders = orderService.findByCurrentUser();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getOrdersByUserId() {
        try {
            List<Order> orders = orderService.findByUserId();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") long id) {
        try {
            Order existingOrder = orderService.getOrder(id);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // admin
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrder(
            @Valid @PathVariable long id
    ) {
        try {
            //  active = false
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Delete order successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    private static OrderDetailDTO getOrderDetailDTO(CartDetail cartDetail, Order order) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderId(order.getId());
        orderDetailDTO.setColor(cartDetail.getColor());
        orderDetailDTO.setPrice(cartDetail.getPrice());
        orderDetailDTO.setNumberOfProduct(cartDetail.getNumberOfProducts());
        orderDetailDTO.setProductId(cartDetail.getProduct().getId());
        orderDetailDTO.setTotalMoney(cartDetail.getPrice() * cartDetail.getNumberOfProducts());
        return orderDetailDTO;
    }
}
