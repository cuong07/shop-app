package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CartDetailDTO;
import com.project.shopapp.models.cart.CartDetail;
import com.project.shopapp.responses.CartDetailResponse;
import com.project.shopapp.services.cart.ICartDetailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/cart-details")
@AllArgsConstructor
public class CartDetailController {
    private final ICartDetailService cartDetailService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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

    @PutMapping("/update-number-of-product")
    private ResponseEntity<?> updateNumberOfProduct(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "quantity") int quantity
           ) {
        try {
            CartDetail cartDetail = cartDetailService.updateCartDetailNumberOfProduct(id, quantity);
            return ResponseEntity.ok(CartDetailResponse.fromCartDetail(cartDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    private ResponseEntity<?> updateCartDetail(@PathVariable("id") Long id,
                                               @Valid @RequestBody CartDetailDTO cartDetailDTO,
                                               BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            CartDetail cartDetail = cartDetailService.updateCartDetail(id, cartDetailDTO);
            return ResponseEntity.ok(cartDetail);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    private ResponseEntity<?> deleteCartDetail(@PathVariable("id") Long id) {
        try {
            cartDetailService.deleteCartDetail(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
