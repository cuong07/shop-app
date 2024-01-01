package com.project.shopapp.controllers;


import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.dtos.OrderPaymentDTO;
import com.project.shopapp.models.cart.Cart;
import com.project.shopapp.models.cart.CartDetail;
import com.project.shopapp.models.order.Order;
import com.project.shopapp.models.user.User;
import com.project.shopapp.models.user.UserAddress;
import com.project.shopapp.responses.BaseResponse;
import com.project.shopapp.responses.SubmitOrderResponse;
import com.project.shopapp.services.cart.ICartDetailService;
import com.project.shopapp.services.cart.ICartService;
import com.project.shopapp.services.order.IOrderDetailService;
import com.project.shopapp.services.order.IOrderService;
import com.project.shopapp.services.payments.IPaymentService;
import com.project.shopapp.services.payments.VNPayService;
import com.project.shopapp.services.user.IUserAddressService;
import com.project.shopapp.services.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("${api.prefix}/payments")
@AllArgsConstructor

public class VNPayController {
    private VNPayService vnPayService;
    private final IOrderService orderService;
    private final IOrderDetailService orderDetailService;
    private final ICartDetailService cartDetailService;
    private final ICartService cartService;
    private final IUserService userService;
    private final IPaymentService paymentService;
    private final IUserAddressService userAddressService;

    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/submit-order")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> submidOrder(
            @RequestParam("amount") int orderTotal,
            @RequestParam("orderInfo") String orderInfo,
            HttpServletRequest request) {
        try {
            SubmitOrderResponse submitOrderResponse = new SubmitOrderResponse();
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
            submitOrderResponse.setMessage("Successfully");
            submitOrderResponse.setUrl(vnpayUrl);
            return ResponseEntity.ok(submitOrderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/vnpay-payment/{userId}")
    public ResponseEntity<?> handleVNPayPayment(
            HttpServletRequest request, Model model,
            @PathVariable("userId") Long userId
    ) {
        try {
            int paymentStatus = vnPayService.orderReturn(request);
            String orderInfo = request.getParameter("vnp_OrderInfo");
            String paymentTime = request.getParameter("vnp_PayDate");
            String transactionId = request.getParameter("vnp_TransactionNo");
            String totalPrice = request.getParameter("vnp_Amount");
            OrderPaymentDTO orderPaymentDTO = new OrderPaymentDTO();
            User user = userService.getUser(userId);

            orderPaymentDTO.setNote(orderInfo);
            orderPaymentDTO.setTotal(Float.parseFloat(totalPrice));
            orderPaymentDTO.setTransactionId(transactionId);
            orderPaymentDTO.setPaymentTime(LocalDateTime.now());
            orderPaymentDTO.setUser(user);
            if (paymentStatus == 1) {
                orderPaymentDTO.setMessage("successfully");
                orderPaymentDTO.setStatus(true);
                orderPaymentDTO.setIsActive(true);
                model.addAttribute("order_id", orderInfo);
                model.addAttribute("total_price", totalPrice);
                model.addAttribute("payment_time", paymentTime);
                model.addAttribute("transaction_id", transactionId);
                model.addAttribute("message", "Successfully");
                paymentService.createOrderPayment(orderPaymentDTO);

                return ResponseEntity.ok(model);
            } else {
                orderPaymentDTO.setMessage("error");
                orderPaymentDTO.setStatus(false);
                orderPaymentDTO.setIsActive(true);
                model.addAttribute("payment_status", paymentStatus);
                model.addAttribute("message", "Failed");
                paymentService.createOrderPayment(orderPaymentDTO);
                return ResponseEntity.ok(model);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/nvpay-payment/order-success")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> handldMapCartToOrder(
            @RequestBody OrderDTO orderDTO
    ) {
        try {
            Order order = orderService.createOrder(orderDTO);
            paymentService.deleteOrderPayment(order.getUser().getId());
            Cart cart = cartService.getCartCurrentByUser();
            for (CartDetail cartDetail : cart.getCartDetails()) {
                OrderDetailDTO orderDetailDTO = getOrderDetailDTO(cartDetail, order);
                orderDetailService.createOrderDetail(orderDetailDTO);
            }
            cartService.deleteCart(cart.getId());
            return ResponseEntity.ok("order success");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/status/nvpay-payment")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Model> getPaymentStatus( Model model) {
        try {
            Boolean status = orderService.getPaymentStatus();
            model.addAttribute("status", status);
            model.addAttribute("message", "successfully");
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            model.addAttribute("status", false);
            model.addAttribute("message", "failed");
            return ResponseEntity.ok(model);
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
