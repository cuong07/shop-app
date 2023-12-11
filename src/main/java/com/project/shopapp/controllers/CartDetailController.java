package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CartDetailDTO;
import com.project.shopapp.models.CartDetail;
import com.project.shopapp.responses.CartDetailResponse;
import com.project.shopapp.services.CartDetailService;
import com.project.shopapp.services.ICartDetailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/cart_details")
@AllArgsConstructor
public class CartDetailController {
    private final ICartDetailService cartDetailService;

    @PostMapping("")
    private ResponseEntity<?> createCartDetail(
            @Valid @RequestBody CartDetailDTO cartDetailDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            CartDetail cartDetail = cartDetailService.createCartDetail(cartDetailDTO);
            return ResponseEntity.ok(CartDetailResponse.fromCartDetail(cartDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}